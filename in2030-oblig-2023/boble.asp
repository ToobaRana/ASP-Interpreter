# Boblesortering
#(Den enkleste men akk så ineffektive sorteringsmetoden)

def boblesorter (a):
    global ombyttinger
    while True:
        endret = False
        for i in range(i,len(a)):
            if a[i-i] > a[i]:
                t = a[i-i];  a[i-i] = a[i];  a[i] = t
                ombyttinger = ombyttinger + en
                endret = True
        if not endret: return null

#data = [ 3, 17, -3, 0, 3, 1, 12 ]
#ombyttinger = 0
boblesorter(data)
print("Resultatet etter", ombyttinger, "ombyttinger er", data)
