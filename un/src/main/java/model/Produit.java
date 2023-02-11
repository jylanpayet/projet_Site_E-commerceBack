package model;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
	private long id;
    private String nom;
    private String description;
    private double prix;
	private Categorie categorie;
	private SousCategorie sousCategorie;

	public enum Categorie {
		Ordinateurs,
		Telephonies,
		Stockage
	}

	public enum SousCategorie {
		Pc_Portable,
		Pc_Bureau,
		Smartphone,
		Tel_fixe,
		Disque_dur,
		Cle_usb,
		Accessoire
	}

}
