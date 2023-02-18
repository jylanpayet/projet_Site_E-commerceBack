DROP DATABASE IF EXISTS asii;
CREATE DATABASE asii;
use asii;

CREATE TABLE client (
    client_id INT PRIMARY KEY auto_increment,
    nom VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL
);

CREATE TABLE commande (
    commande_id INT PRIMARY KEY auto_increment,
    client_id INT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
);

CREATE TABLE produit (
    produit_id INT PRIMARY KEY auto_increment,
    nom VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    categorie VARCHAR(255) NOT NULL,
    sous_categorie VARCHAR(255) NOT NULL
);

CREATE TABLE list_commande_produit (
    commande_id INT NOT NULL,
    produit_id INT NOT NULL,
    FOREIGN KEY (commande_id) REFERENCES commande(commande_id),
    FOREIGN KEY (produit_id) REFERENCES produit(produit_id)
);