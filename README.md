# Facîle 🏝️✅

Planificateur d'itinéraires optimisés tout-en-un, vers ou depuis Belle-Île-en-Mer !🚊🚢🚗🕰️

## Contexte ℹ️
Les voyages vers (et depuis) Belle-Île-En-Mer en transports en commun sont souvent synonymes de longs trajets et de galères. À part si l'on est en voiture, les correspondances s'enchaînent et la diversification des opérateurs les rend le plus souvent très peu optimisés.

Le but de Facîle est de créer un outil tout-en-un, permettant de planifier facilement un trajet vers (ou depuis) Belle-Île. En précisant simplement la date et le lieu de départ, l'application assemble les divers moyens de transports et propose un trajet optimisé (le plus court / avec le moins de correspondances possibles).

## Stack Technique 🛠️
- **Backend** : Quarkus (avec Java 21).
- **BDD** : PostgreSQL
- **Frontend** : Angular 19+. Utilisation de la librairie de composants Angular Materials

## Avancement 📅
- [x] Planificateur d'itinéraires de trajets en train fonctionnel (jusqu'à Auray seulement, gare TGV principale pour Belle-île)
- [x] Choix de la date du trajet
- [x] Création et optimisation des trajets (TGV grandes lignes)
- [ ] Affichage des détails de chaque trajet
- [ ] Compléter les itinéraires (Auray -> Belle-île) avec intégration des bateaux
- [ ] Ajout de modes de transports alternatifs (voiture, bus)
- [ ] Enregistrement des trajets
- [ ] Partage de trajets

## Précisions 🔍
- Les données sur les trains proviennent de l'API Navitia gratuite, outil de démonstration. Les données sont limitées à +22 jours seulement et peu optimisées.
- En l'absence d'API grand public pour les horaires des bateaux, ces horaires sont entrés en dur en base de données.

⚠️ Ceci reste un prototype, l'optimisation des trajets n'est pas parfaite (pour le moment ce n'est pas le but).

## But 🎒
**Pédagogique** :
- Devenir un meilleur développeur
- Apprendre une stack particulière avec séparation backend / frontend (Quarkus / Angular).
- Création d'une API REST
- Création d'une interface fonctionnelle, simple et intuitive

**Loisir** :
- Me faire plaisir et créer un outil qui pourrait réellement m'être utile !

## Contact ☎️
Pour toute question ou commentaire, vous pouvez me contacter par mail : marinboone56@gmail.com

🔒Copyright © 2025 Marin Boone. Tous droits réservés.
