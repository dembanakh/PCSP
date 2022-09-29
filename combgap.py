import itertools as it

n = 6
k = 4

I = {(v,): (0,) for v in range(n)}
s = {(v,): v for v in range(n)}

for quad in it.combinations(range(n), 4):
    print(quad)
