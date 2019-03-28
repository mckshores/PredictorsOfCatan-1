import csv
import os
import numpy as np
import tensorflow as tf

#data = list(csv.reader(open("processed.csv")))
#print(data[2][2])
#arr = np.array(data[1])
#print(arr[1])
#print(arr.dtype)
#tensor = tf.convert_to_tensor(arr, tf.int32)
#session = tf.Session()
#print(session.run(tensor))

dir_path = os.path.dirname(os.path.realpath(__file__))
filename = dir_path + "/processed.csv"

features = tf.placeholder(tf.int64, shape=[6], name='features')
rounds = tf.placeholder(tf.string, name='round')
win = tf.reduce_sum(features, name='win')

printerop = tf.print(win, [rounds, features, win], name='printer')
with tf.Session() as sess:
    sess.run( tf.global_variables_initializer())
    with open(filename) as inf:
        # Skip header
        next(inf)
        for line in inf:
            # Read data, using python, into our features
            round, resource_strength, hand_strength, vp_total, cities, dev_cards, win = line.strip().split(",")
            #player_code = int(player_code)
            resource_strength = int(resource_strength)
            hand_strength = int(hand_strength)
            vp_total = int(vp_total)
            cities = int(cities)
            dev_cards = int(dev_cards)
            win = int(win)
            # Run the Print ob
            win = sess.run(printerop, feed_dict={features: [resource_strength, hand_strength, vp_total, cities, dev_cards, win], rounds:round})
            print(rounds, win)