## Statistische Untersuchung der Datensätze Brezeln

#Dateipfad + Übertragung auf Variable brez
brez <- read.table("/Users/lukasritter/Brezeln1.csv", dec=",")
brez <- data.matrix(brez) 


#Bereichung Mittelwert (mean), Varianz (var), Standardabweichung (sd), Schiefe(skew), Kurtosis (kurt)
mean <- mean(brez)
var <- var(brez)
sd <- sd(data.matrix(brez))
skew <- skewness(brez) # NV hat Schiefe 0 - Beschreibt Art + Stärke der Symmetrie - 0.193 bei aktuellem Datensatz 
kurt <- kurtosis(brez) # NV hat Kurtosis 3 - Beschreibt Steilheit / Spitzigkeit - 2.74 bei aktuellem Datensatz
#Berechnete Werte der Schiefe + Kurtosis beschreibt grundsätzlich eine Normalverteilung 


#Test auf Normalverteiung
library(moments) #Package notwendig für Berechnung
## Durchführung Jarque-Bera-Test
# Interpretation des Ergebnisses
# hoher p-wert hier gut, da H0 = Daten normalverteilt
jarque.test(as.vector(brez))


## Konfidentintervall 
# Schätzung für Anzahl an Brezeln 
x <- 37
high <- x*mean + qnorm(0.975)*sqrt(x)*sd
low <- x*mean - qnorm(0.975)*sqrt(x)*sd
## [low,high] geben dir jz dein Konfidenzintervall an, gegeben dass 37 Brezeln drin sind 
#analog kannst es für 36 oder 38 machen, einfach x durch  (x-1) oder x+1 ersetzen


# Plots
# Plot 1 , spielt mit den breaks bisschen rum würde aber 5,6 oder 7 empfehlen
hist(brez,breaks = 6,freq = F, main = "Brezeln", xlab = "Gewicht in g", col = "lightgrey")
curve(dnorm(x, mean=mean(brez), sd=sd(brez)), col="darkblue", lwd=2, add=TRUE)

# Plot 2
# hier am besten mit parametern breaks und zweites argument von ylim aktuell 12 rumspielen 
# orangene kurve ist eine dichteschätzung, blaue kurve zeigt die Normalverteilung
myhist <- hist(brez, breaks = 10, main = "Histogram of Brezeln", xlab = "Gewicht in g",xlim = c(129,140) ,ylim = c(0,12) ,col = "lightgrey", )
multiplier <- myhist$counts / myhist$density
mydensity <- density(brez)
mydensity$y <- mydensity$y * multiplier[1]

lines(mydensity, lwd =2, col = "orange")

myx <- seq(min(brez), max(brez), length.out= 100)
mymean <- mean(brez)
mysd <- sd(brez)

normal <- dnorm(x = myx, mean = mymean, sd = mysd)
lines(myx, normal * multiplier[1], col = "blue", lwd = 2)

sd_x <- seq(mymean - 3 * mysd, mymean + 3 * mysd, by = mysd)
sd_y <- dnorm(x = sd_x, mean = mymean, sd = mysd) * multiplier[1]

# rote balken je standardabweichung (kann man auch weglassen)
segments(x0 = sd_x, y0= 0, x1 = sd_x, y1 = sd_y, col = "red1", lwd = 4)





