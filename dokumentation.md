// section1
# Idee für LVP Project
Mir kamm eine Labyrinth als Idee, da ich intressiert war mit welchen Algorithmen man dies erstellen und Lösen kann.

Für den Labyrinth aufbau habe ich mir verschiedenste Algrithem auf Wikepedia angeschaut. Und habe mich für ein rekursiven stacked based algorithmus  entschiden.

Für die implemantation habe ich mich für eine Iterative entschiden anstatt eine Rekusrive zu nehemen da ich sehen wollte wie groß ich das Labyrinth bekommen kann und bei Rekursion hatte ich angst, dass mein Arbeitsspeicher überlastet wird.

Für den Labyringth Solver algorithmus habe ich es selbst versucht mit einem Backtracking algorithmus der ähnlich war zu einem den ich in der Schule in Informatik gelernt habe.

# Probleme und Limitationen
Mein Ziel war ja zu sehen wie groß das Labyrinth werden kann jedoch bin ich auf unerwartete Problem in meine optimisation gestoßen. Ich habe mich erkundigt und gefunden das BitSet sehr viel effektiver ist 0 und 1 zu speicher als Int das hatte auch geholfen habe ich aber nicht implementiert da dies nie der limentierende Faktor war. 

 Das problem ist der Browserfenster Arbeitsspeicher gewesen den der String der ihn durch Print übergeben wird wurde sehr lange bei zum beispiel 10000 x 10000 und da macht es auch kein unterschied wie effizient mein java code ist da ich am Ende immer ein String übergeben muss.Ich habe versucht den String irgendwie zu encoden um ihn effizienter zu machen jdoch leider ohne Erfolg.

Mögliche Lösungen wären LVP eine art Stream funktion zu geben um so datenpackete dem Browser nacheinander shicken zu können so das der diese verarbeiten und Löschen kann. Oder ich hätte das Labyrinth als bild umwandeln können vielleicht in Java dann hätte ich es jedoch keine Animation geben können wie es gelöst wird.
// section1
// section2
# Einsatz von KI
- Fehlersuche und Behebung
- Großteil vom Javascript code für das rendern des Labyringths im Browser und für die Animation für das lösen wurden von KI erstellt jedoch hatte animation Probleme und musste durch probieren mithilfe von KI gelöst werden
- Hilfe zur Einschätzung von Projekt größe und komplexität
// section2