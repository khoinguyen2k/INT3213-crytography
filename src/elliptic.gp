default(parisize,"1600M")

a=180
b=3536
p=1986181080048982133654031961719942703022737256317820295873954303129190220078988097483363098098394348954253427763979256167416182433022201
E=ellinit([a,b])
print("p is prime: "isprime(p))
count=ellap(E,p)

while(!isprime(p+1-count),a=a+1;E=ellinit([a,b]);count=ellap(E,p))

print("a="a)
print("num of points="p+1-count)
