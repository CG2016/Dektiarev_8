import matplotlib.pyplot as plt

with open('res.txt') as f:
    arr = []
    for line in f:
        arr.append([int(num) for num in line.split()])
    plt.imshow(arr, interpolation='none')
    plt.show()
