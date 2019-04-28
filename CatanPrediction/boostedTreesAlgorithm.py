from __future__ import absolute_import, division, print_function, unicode_literals
from matplotlib import pyplot as plt
from sklearn.metrics import roc_curve
from sklearn.metrics import roc_auc_score
import pandas as pd
import tensorflow as tf
import sys

tf.enable_eager_execution()

tf.logging.set_verbosity(tf.logging.ERROR)
tf.set_random_seed(100)

trainingSet = pd.read_csv(sys.argv[1], index_col=False)
testingSet = pd.read_csv(sys.argv[2], index_col=False)
yTrain = trainingSet.pop('Winner')
yTest = testingSet.pop('Winner')
featureColumns = tf.feature_column
numerics = ['R-Strength', 'H-Strength', 'VP', 'Cities', 'Dev', 'Round']

columns = []
for label in numerics:
    columns.append(featureColumns.numeric_column(label, dtype=tf.float32))
examples = len(yTrain)


def makeInputFunction(j, k, n_epochs=None, shuffle=True):
    def inputFunction():
        dataset = tf.data.Dataset.from_tensor_slices((dict(j), k))
        if shuffle:
            dataset = dataset.shuffle(examples)
        dataset = dataset.repeat(n_epochs)
        dataset = dataset.batch(examples)
        return dataset
    return inputFunction


trainingInputFunction = makeInputFunction(trainingSet, yTrain, n_epochs=10)
testingInputFunction = makeInputFunction(testingSet, yTest, n_epochs=1, shuffle=False)

estimation = tf.estimator.BoostedTreesClassifier(columns, n_batches_per_layer=5)
estimation.train(trainingInputFunction, max_steps=100)
results = estimation.evaluate(testingInputFunction)

predictionDictionary = list(estimation.predict(testingInputFunction))
probabilities = pd.Series([prediction['probabilities'][1] for prediction in predictionDictionary])

predictions = []
begin = 0
end = 4


def findWinner(probs):
    largest = begin
    for z in range(begin, end):
        if probs[z] > probs[largest]:
            largest = z
    predictions.append((largest % 4) + 1)


while end <= len(probabilities):
    findWinner(probabilities[begin:end])
    begin = begin + 4
    end = end + 4

modifiedProbabilities = []
twoDProbabilites = []
for x in range(len(predictions)):
    twoDProbabilites.append([0, 0, 0, 0])
    twoDProbabilites[x][predictions[x] - 1] = 1
for x in twoDProbabilites:
    for y in x:
        modifiedProbabilities.append(y)

count = 0
total = len(yTest)
for x in range(len(yTest)):
    if modifiedProbabilities[x] == yTest[x]:
        count = count + 1
print("Accuracy:", count / total)

print("AUC:", roc_auc_score(yTest, modifiedProbabilities))

if 'ROC' in sys.argv[3]:
    fpr, tpr, _ = roc_curve(yTest, modifiedProbabilities)
    plt.plot(fpr, tpr)
    plt.title('ROC curve')
    plt.xlabel('false positive rate')
    plt.ylabel('true positive rate')
    plt.xlim(0,)
    plt.ylim(0,)
    plt.show()

if "HISTOGRAM" in sys.argv[3]:
    probs = pd.Series(predictions)
    probs.plot(kind='hist', title='Predicted Winners')
    plt.show()
