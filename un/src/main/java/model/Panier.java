package model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Panier {
    private List<Pair<Integer,Produit>> panier = new ArrayList<>();
}
