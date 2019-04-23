from __future__ import absolute_import, division, print_function, unicode_literals
from matplotlib import pyplot as plt
from sklearn.metrics import roc_curve
import pandas as pd
import tensorflow as tf
import sys

tf.enable_eager_execution()

tf.logging.set_verbosity(tf.logging.ERROR)
tf.set_random_seed(100)

trainingSet = pd.read_csv(sys.argv[1])
testingSet = pd.read_csv(sys.argv[2])
yTrain = trainingSet.pop('Winner')
yTest = testingSet.pop('Winner')
featureColumns = tf.feature_column
numerics = ['R-Strength', 'H-Strength', 'VP', 'Cities', 'Dev', 'Round']

columns = []
for label in numerics:
    columns.append(featureColumns.numeric_column(label, dtype=tf.float32))
examples = len(yTrain)


def makeInputFunction(x, y, n_epochs=None, shuffle=True):
    def inputFunction():
        dataset = tf.data.Dataset.from_tensor_slices((dict(x), y))
        if shuffle:
            dataset = dataset.shuffle(examples)
        dataset = dataset.repeat(n_epochs)
        dataset = dataset.batch(examples)
        return dataset
    return inputFunction


trainingInputFunction = makeInputFunction(trainingSet, yTrain)
testingInputFunction = makeInputFunction(testingSet, yTest, n_epochs=1, shuffle=False)

estimation = tf.estimator.BoostedTreesClassifier(columns, n_batches_per_layer=1)
estimation.train(trainingInputFunction, max_steps=100)
results = estimation.evaluate(testingInputFunction)


def makeInMemoryTrainInputFunction(x, y):
    def inputFunction():
        return dict(x), y
    return inputFunction


trainingInputFunction = makeInMemoryTrainInputFunction(trainingSet, yTrain)
testingInputFunction = makeInputFunction(testingSet, yTest, n_epochs=1, shuffle=False)
print('Accuracy', estimation.evaluate(testingInputFunction)['accuracy'])

predictionDictionary = list(estimation.predict(testingInputFunction))
probabilities = pd.Series([prediction['probabilities'][1] for prediction in predictionDictionary])

largest = 0
for z in range(len(probabilities)):
    if probabilities[z] > probabilities[largest]:
        largest = z
print('Predicted Winner:', largest)

if sys.argv[3] == 'true':
    fpr, tpr, _ = roc_curve(yTest, probabilities)
    plt.plot(fpr, tpr)
    plt.title('ROC curve')
    plt.xlabel('false positive rate')
    plt.ylabel('true positive rate')
    plt.xlim(0,)
    plt.ylim(0,)
    plt.show()
