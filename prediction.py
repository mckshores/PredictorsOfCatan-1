import csv
import os
import numpy as np
import tensorflow as tf


dir_path = os.path.dirname(os.path.realpath(__file__))
filename = dir_path + "/testProcessed.csv"

features = tf.placeholder(tf.int64, shape=[6], name='features')
player = tf.placeholder(tf.string, name='player')
win = tf.reduce_sum(features, name='win')

printerop = tf.print(win, [player, features, win], name='printer')
with tf.Session() as sess:
    sess.run( tf.global_variables_initializer())
    with open(filename) as inf:
        # Skip header
        next(inf)
        for line in inf:
            # Read data, using python, into our features
            player_code, resource_strength, hand_strength, vp_total, cities, dev_cards, rounds, win = line.strip().split(",")
            player_code = int(player_code)
            resource_strength = int(resource_strength)
            hand_strength = int(hand_strength)
            vp_total = int(vp_total)
            cities = int(cities)
            dev_cards = int(dev_cards)
            # Run the Print ob
            win = sess.run(printerop, feed_dict={features: [resource_strength, hand_strength, vp_total, cities, dev_cards, rounds, win], player:player_code})
            print(player_code, win)