# MyBestLocation
(JSONParser.java) 
Projet mobile avec Android Studio
DashboardFragment:-EditText longitude
                  -EditText latitude
                  -EditText description
                  -ImageButton Map    ---onClick--->  MapActivity start >>> on choisit un lieu >>> le map se ferme et dans DashboardFragment longitude et latitude prennent les valeurs réelles de lieu choisi 
                  -ImageButton Ajout  ---onClick--->  si les champs sont vides -----> Toast "Remplir les champs"
                                                      si non                   -----> Inserer les champs dans la base de données >>> vider les champs
NotificationFragment:
                      -ListeView : >en utilisant un Adapter personaliser affiche la liste de positions ajoutées avec leur cordonnées :
                                                      *TextView Description
                                                      *TextView Longitude
                                                      *TextView Latitude
                                                      *ImageButton delete ---onClick--->AlertDialog : si ---onclick---> Positive >>> supprimer la position de la base de données + actualisation de l'affichage
                                                      *ImageButton Map    ---onClick--->Ouvrir un map en mettant le 'marker' sur le lieu avec langitude et latitude de la position 
                                   
                                                      
                  

