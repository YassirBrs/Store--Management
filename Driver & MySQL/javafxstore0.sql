-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  sam. 25 jan. 2020 à 00:01
-- Version du serveur :  10.4.8-MariaDB
-- Version de PHP :  7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `javafxstore`
--

-- --------------------------------------------------------

--
-- Structure de la table `bank`
--

CREATE TABLE `bank` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `nom`, `description`) VALUES
(12, 'iphone', 'best phone'),
(13, 'samsung', 'phone mark'),
(16, 'hhhh', 'fffff'),
(18, 'Aliments et boissons à base de végétaux', 'Alimentation'),
(19, 'Aliments dorigine végétale', 'Alimentation'),
(20, 'Conserves', 'Alimentation'),
(21, 'Aliments à base de fruits et de légumes', 'Alimentation'),
(22, 'Fruits et produits dérivés', 'Alimentation'),
(23, 'Fruits en conserve', 'Alimentation'),
(24, 'Desserts', 'Alimentation'),
(25, 'Fruits au sirop', 'Alimentation'),
(26, 'Plats préparés', 'Alimentation'),
(27, 'Boissons', 'Alimentation'),
(28, 'Légumes et dérivés', 'Alimentation'),
(29, 'Epicerie', 'Alimentation'),
(30, 'Surgelés', 'Alimentation'),
(31, 'Graines', 'Alimentation'),
(32, 'Sauces', 'Alimentation');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `ville` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `telephone`, `ville`) VALUES
(4, 'yassir', 'bouras', '0655937493', 'Agadir'),
(5, 'issam', 'banoun', '0723645273', 'Mohammedia'),
(6, 'imad', 'naania', '0655749378', 'agadir'),
(7, 'john', 'wick', '06743853345', 'New York'),
(8, 'van', 'diesel', '077354839', 'Los Angelos'),
(11, 'victor', 'hugo', '0612345678', 'Paris'),
(12, 'Ahmed', 'Seffrioui', '0612345678', 'Fes'),
(13, 'Salif ', 'Keita', '0612345678', 'Dakar'),
(14, 'Omar', 'Wahdani', '0612345678', 'Paris'),
(15, 'Samir', 'Lkazaze', '0612345678', 'Dchaira'),
(16, 'omar ', 'wahdani', '76452', 'sale');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `id` int(11) NOT NULL,
  `solde` double NOT NULL,
  `idclient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id`, `solde`, `idclient`) VALUES
(1, 500000, 4),
(2, 400000, 5);

-- --------------------------------------------------------

--
-- Structure de la table `lignevente`
--

CREATE TABLE `lignevente` (
  `id` int(11) NOT NULL,
  `id_vente` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `qte` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `lignevente`
--

INSERT INTO `lignevente` (`id`, `id_vente`, `id_produit`, `qte`) VALUES
(40, 16, 1, 5),
(41, 17, 2, 12),
(42, 16, 2, 4),
(43, 16, 4, 2),
(44, 18, 1, 4),
(45, 18, 2, 7),
(46, 18, 7, 7),
(47, 19, 1, 7),
(48, 19, 2, 2),
(49, 20, 26, 5),
(50, 20, 25, 2),
(51, 20, 3, 1),
(52, 21, 4, 133),
(53, 22, 7, 1),
(54, 23, 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id` int(11) NOT NULL,
  `id_vente` int(11) NOT NULL,
  `montant` double NOT NULL,
  `date` varchar(10) NOT NULL,
  `status` text NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `paiement`
--

INSERT INTO `paiement` (`id`, `id_vente`, `montant`, `date`, `status`, `type`) VALUES
(35, 16, 12323, '06-01-2020', 'Payer', 'Espèce'),
(36, 17, 3000, '22-01-2020', 'Payer', 'Traite'),
(38, 17, 33, '16-01-2020', 'Payer', 'Espèce'),
(39, 17, 300, '11-01-2020', 'Non Payer', 'Espèce'),
(40, 17, 1111, '22-01-2020', 'Payer', 'Espèce'),
(41, 16, 10000, '12-01-2020', 'Payer', 'Espèce'),
(42, 18, 10000, '12-01-2020', 'Payer', 'Espèce'),
(43, 18, 15000, '12-01-2020', 'Payer', 'Espèce'),
(45, 16, 10000, '14-01-2020', 'Payer', 'Virement'),
(46, 18, 150000, '15-01-2020', 'Payer', 'Virement'),
(47, 19, 10000, '14-01-2020', 'Payer', 'Espèce'),
(48, 19, 10000, '14-01-2020', 'Payer', 'Virement'),
(49, 17, 3000, '15-01-2020', 'Payer', 'Virement'),
(50, 19, 2342, '15-01-2020', 'Payer', 'Virement'),
(53, 19, 2300, '15-01-2020', 'Payer', 'Virement'),
(58, 19, 1444, '15-01-2020', 'Payer', 'Chèque'),
(59, 20, 8000, '15-01-2020', 'Payer', 'Espèce'),
(60, 20, 10, '15-01-2020', 'Non Payer', 'Traite'),
(61, 20, 101, '15-01-2020', 'Payer', 'Virement'),
(62, 20, 101, '15-01-2020', 'Payer', 'Virement'),
(63, 20, 1, '15-01-2020', 'Payer', 'Virement'),
(64, 20, 1, '15-01-2020', 'Payer', 'Virement'),
(65, 20, 10, '15-01-2020', 'Payer', 'Espèce'),
(67, 20, 20, '15-01-2020', 'Payer', 'Chèque'),
(68, 22, 30000, '15-01-2020', 'Payer', 'Espèce'),
(69, 23, 2000, '16-01-2020', 'Non Payer', 'Espèce'),
(72, 23, 4000, '16-01-2020', 'Non Payer', 'Traite'),
(73, 23, 4000, '16-01-2020', 'Non Payer', 'Traite'),
(74, 23, 4000, '16-01-2020', 'Non Payer', 'Traite');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `designation` varchar(255) NOT NULL,
  `prix` double NOT NULL,
  `catid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `designation`, `prix`, `catid`) VALUES
(1, 'iphone x', 6000, 12),
(2, 'Afficheur LCD', 888, 12),
(3, 'Samsung 45\'', 8882, 12),
(4, 'Recepteur', 555, 13),
(5, 'case', 10, 12),
(7, 'BMW X5', 44400, 13),
(9, 'Straw Mushrooms', 100, 20),
(10, 'Piment', 55, 20),
(11, 'Soupe', 40, 20),
(12, 'Nefles', 35, 20),
(13, 'Serise', 45, 20),
(14, 'Aicha Tomate', 7, 20),
(15, 'Maîs doux', 16, 20),
(16, 'Mange', 35, 24),
(17, 'Palm Seeds', 40, 24),
(18, 'Longans', 45, 24),
(19, 'Coca Cola', 7, 27),
(20, 'Pepsi', 7, 27),
(21, 'Hawaii', 7, 27),
(22, 'Fanta', 8, 27),
(23, 'Ice', 5, 27),
(24, 'Soja Gratin', 50, 29),
(25, 'chocolat noir', 8, 29),
(26, 'Sucre 1Kg', 8, 29),
(27, 'Banane 1Kg', 10, 24),
(28, 'Poulet 2Kg', 40, 26),
(29, 'Poisson 2Kg', 150, 26),
(30, 'Barbecue 1Kg', 70, 26);

-- --------------------------------------------------------

--
-- Structure de la table `transfert`
--

CREATE TABLE `transfert` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `rib` varchar(100) NOT NULL,
  `bank` varchar(100) NOT NULL,
  `montant` double NOT NULL,
  `date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `transfert`
--

INSERT INTO `transfert` (`id`, `nom`, `prenom`, `rib`, `bank`, `montant`, `date`) VALUES
(1, 'yassir', 'bouras', '345357567467', 'ATTIJARI BANK', 2000, '12-01-2020'),
(2, 'yassir', 'bouras', '879443743346', 'BANK', 10000, '14-01-2020'),
(3, 'yassir', 'bouras', '87234687234', 'BANK', 10000, '14-01-2020'),
(4, 'van', 'diesel', '728345287345', 'BANK', 3000, '15-01-2020'),
(5, 'yassir', 'bouras', '234324234', 'BANK', 2300, '15-01-2020'),
(6, 'yassir', 'bouras', '2342345435234', 'BANK', 231, '15-01-2020'),
(7, 'yassir', 'bouras', '23453245', 'BANK', 222, '15-01-2020'),
(8, 'issam', 'banoun', '3247253745234', 'BMCE', 101, '15-01-2020'),
(9, 'issam', 'banoun', '37453827234', 'CIH', 1, '15-01-2020');

-- --------------------------------------------------------

--
-- Structure de la table `vente`
--

CREATE TABLE `vente` (
  `id` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `date` varchar(10) NOT NULL,
  `imprimer` varchar(20) NOT NULL DEFAULT 'non'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `vente`
--

INSERT INTO `vente` (`id`, `id_client`, `date`, `imprimer`) VALUES
(16, 7, '08-01-2020', 'non'),
(17, 8, '10-01-2020', 'non'),
(18, 6, '12-01-2020', 'non'),
(19, 4, '12-01-2020', 'non'),
(20, 5, '15-01-2020', 'non'),
(21, 11, '15-01-2020', 'oui'),
(22, 5, '15-01-2020', 'non'),
(23, 6, '16-01-2020', 'oui');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bank`
--
ALTER TABLE `bank`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `lignevente`
--
ALTER TABLE `lignevente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `myForeignKey` (`id_vente`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_vente` (`id_vente`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `catid` (`catid`);

--
-- Index pour la table `transfert`
--
ALTER TABLE `transfert`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vente`
--
ALTER TABLE `vente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_client` (`id_client`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bank`
--
ALTER TABLE `bank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `lignevente`
--
ALTER TABLE `lignevente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `transfert`
--
ALTER TABLE `transfert`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `vente`
--
ALTER TABLE `vente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `lignevente`
--
ALTER TABLE `lignevente`
  ADD CONSTRAINT `lignevente_ibfk_1` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `myForeignKey` FOREIGN KEY (`id_vente`) REFERENCES `vente` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `paiement_ibfk_1` FOREIGN KEY (`id_vente`) REFERENCES `vente` (`id`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `produit_ibfk_1` FOREIGN KEY (`catid`) REFERENCES `categorie` (`id`);

--
-- Contraintes pour la table `vente`
--
ALTER TABLE `vente`
  ADD CONSTRAINT `vente_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
