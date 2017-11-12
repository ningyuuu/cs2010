numbers = [-7, 10, 9, 2, 3, 8, 8, 1, 99999]
A = [0 for n in numbers]

for i in range(0, len(numbers)-1):
	print('checking with', i, 'as base')
	for j in range(i+1, len(numbers)):
		if numbers[i] < numbers[j]:
			if A[j] <= A[i]:
				print('stretch', j, 'from', i)
				A[j] = A[i] + 1

print(A)