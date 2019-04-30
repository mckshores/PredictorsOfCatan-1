from __future__ import absolute_import, division, print_function, unicode_literals
from matplotlib import pyplot as plt
from sklearn.metrics import roc_curve
from sklearn.metrics import roc_auc_score
import pandas as pd
import tensorflow as tf
import sys

# Use eager execution. This is part of TensorFlow. It allows for faster processing.
tf.enable_eager_execution()

tf.logging.set_verbosity(tf.logging.ERROR)
tf.set_random_seed(100)

# In the proper directory the line to run this program in the terminal is:
# python boostedTreesAlgorithm.py "traingset.csv" "testingset.csv" "HISTOGRAM ROC"
# The last argument can be "HISTOGRAM" or "ROC" or both or None
# Putting HISTOGRAM or ROC is what prints those  graphs. If you don't want those to print, don't include them.

# Read in the training and testing sets
trainingSet = pd.read_csv(sys.argv[1], index_col=False)
testingSet = pd.read_csv(sys.argv[2], index_col=False)
# Pop off the target features from both sets
yTrain = trainingSet.pop('Winner')
yTest = testingSet.pop('Winner')

# Create the feature columns. These are based on the actual columns in the csv files
featureColumns = tf.feature_column
numerics = ['R-Strength', 'H-Strength', 'VP', 'Cities', 'Dev', 'Round']
columns = []
for label in numerics:
    columns.append(featureColumns.numeric_column(label, dtype=tf.float32))
examples = len(yTrain)


# Make the input function.
# The input function is used to feed the data into the estimator
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

# This estimator uses boosted trees.
# It trains, builds a model, tests the model, identifies the errors,
# then goes back to training taking into account the error from the previous model.
estimator = tf.estimator.BoostedTreesClassifier(columns, n_batches_per_layer=5)
# Train the model
estimator.train(trainingInputFunction, max_steps=100)
# Test the model against the testing set
results = estimator.evaluate(testingInputFunction)

# Get the actual predictions made by the model.
# The boosted trees model will return a value between 0 and 1
predictionDictionary = list(estimator.predict(testingInputFunction))
probabilities = pd.Series([prediction['probabilities'][1] for prediction in predictionDictionary])

predictions = []
begin = 0
end = 4


# To find just one winner for each round, we have to go through the predictions and find which value
# out of every four is the maximum. This is done because when we collect the data from the generators
# each round generates four instances, one for every player. So one round is represented by four
# lines of data.
def findWinner(probs):
    largest = begin
    for z in range(begin, end):
        if probs[z] > probs[largest]:
            largest = z
    predictions.append((largest % 4) + 1)


# Go through the data in groups of four for reasons above.
while end <= len(probabilities):
    findWinner(probabilities[begin:end])
    begin = begin + 4
    end = end + 4

modifiedProbabilities = []
twoDProbabilites = []
# After figuring out who was the winner for every four lines, it then has to be converted back to binary
# so that it can be compared with the original values from the test set.
for x in range(len(predictions)):
    twoDProbabilites.append([0, 0, 0, 0])
    twoDProbabilites[x][predictions[x] - 1] = 1
for x in twoDProbabilites:
    for y in x:
        modifiedProbabilities.append(y)

count = 0
total = len(yTest)
# Go through the target features from the testing set and compare these with the predicted values to determine accuracy
for x in range(len(yTest)):
    if modifiedProbabilities[x] == yTest[x]:
        count = count + 1
print("Accuracy:", count / total)

# Determine the Area Under the Curve of the ROC curve
print("AUC:", roc_auc_score(yTest, modifiedProbabilities))

# Show the ROC curve only if it was included in the last argument to the program
if 'ROC' in sys.argv[3]:
    fpr, tpr, _ = roc_curve(yTest, modifiedProbabilities)
    plt.plot(fpr, tpr)
    plt.title('ROC curve')
    plt.xlabel('false positive rate')
    plt.ylabel('true positive rate')
    plt.xlim(0,)
    plt.ylim(0,)
    plt.show()

# Show the histogram of the predictions made by the model only if it was included in the last argument to the program
if "HISTOGRAM" in sys.argv[3]:
    probs = pd.Series(predictions)
    probs.plot(kind='hist', title='Predicted Winners')
    plt.show()
