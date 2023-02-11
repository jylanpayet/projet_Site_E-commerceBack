package model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Panier {
    private Map<Integer, Produit> panier = new HashMap<>();
}
