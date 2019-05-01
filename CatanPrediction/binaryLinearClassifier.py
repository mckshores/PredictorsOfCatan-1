from matplotlib import pyplot as plt
from sklearn.metrics import roc_curve
import tensorflow as tf
import pandas as pd

tf.enable_eager_execution()

allfeatures = ['resource_strength', 'hand_strength', 'vp', 'cities', 'dev_cards', 'round', 'win']
train_path = "D:\\binaryTraining.csv"
test_path = "D:\\binaryTrial.csv"
roc = False
trainingSet = pd.read_csv(train_path)
testingSet = pd.read_csv(test_path)
yTrain = trainingSet.pop('Winner')
yTest = testingSet.pop('Winner')
featureColumns = tf.feature_column
numerics = ['R-Strength', 'H-Strength', 'VP', 'Cities', 'Dev', 'Round']

features = []
for label in numerics:
    features.append(featureColumns.numeric_column(label, dtype=tf.float32))
examples = len(yTrain)

def get_input_fn(x, y, num_epochs=None, shuffle=True):
    def inputFunction():
        dataset = tf.data.Dataset.from_tensor_slices((dict(x), y))
        if shuffle:
            dataset = dataset.shuffle(examples)
        dataset = dataset.repeat(num_epochs)
        dataset = dataset.batch(examples)
        return dataset
    return inputFunction

trainingInputFunction = get_input_fn(trainingSet, yTrain, num_epochs=1, shuffle=True)
testingInputFunction = get_input_fn(testingSet, yTest, num_epochs=1, shuffle=False)

model = tf.estimator.LinearClassifier(model_dir=None,feature_columns=features)

model.train(trainingInputFunction, steps=100)

print(model.evaluate(testingInputFunction, steps=1000))

predictionDictionary = list(model.predict(testingInputFunction))

probabilities = pd.Series([prediction['probabilities'][1] for prediction in predictionDictionary])

largest = 0
for z in range(len(probabilities)):
    if probabilities[z] > probabilities[largest]:
        largest = z
#print('Predicted Winner:', largest)
    print(probabilities[z])

if roc is True:
    fpr, tpr, _ = roc_curve(yTest, probabilities)
    plt.plot(fpr, tpr)
    plt.title('ROC curve')
    plt.xlabel('false positive rate')
    plt.ylabel('true positive rate')
    plt.xlim(0,)
    plt.ylim(0,)
    plt.show()