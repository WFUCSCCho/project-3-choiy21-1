import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('analysis.txt')

#print(df.head())

# 1) sorted time
plt.figure()
for algo in df['Algorithm'].unique():
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['SortedTime(ns)'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Sorted Time (ns)')
plt.title('Sorting Time for Already Sorted Lists')
plt.legend()
plt.show()

# 2) shuffled time
plt.figure()
for algo in df['Algorithm'].unique():
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['ShuffledTime(ns)'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Shuffled Time (ns)')
plt.title('Sorting Time for Shuffled Lists')
plt.legend()
plt.show()

# 3) reversed time
plt.figure()
for algo in df['Algorithm'].unique():
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['ReversedTime(ns)'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Reversed Time (ns)')
plt.title('Sorting Time for Reversed Lists')
plt.legend()
plt.show()

# 4) sorted comparisons
plt.figure()
for algo in ['bubble', 'transposition']:
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['SortedComparisons'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Sorted Comparisons')
plt.title('Sorted Comparisons for Bubble and Transposition Sorts')
plt.legend()
plt.show()

# 5) shuffled comparisons
plt.figure()
for algo in ['bubble', 'transposition']:
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['ShuffledComparisons'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Shuffled Comparisons')
plt.title('Shuffled Comparisons for Bubble and Transposition Sorts')
plt.legend()
plt.show()

# 6) reversed comparisons
plt.figure()
for algo in ['bubble', 'transposition']:
    subset = df[df['Algorithm'] == algo]
    plt.plot(subset['Lines'], subset['ReversedComparisons'], label=algo)
plt.xlabel('Lines')
plt.ylabel('Reversed Comparisons')
plt.title('Reversed Comparisons for Bubble and Transposition Sorts')
plt.legend()
plt.show()