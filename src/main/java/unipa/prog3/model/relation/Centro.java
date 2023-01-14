package unipa.prog3.model.relation;

public class Centro extends Relation {
    public Centro(String città, String stato) {
        super(2, 0);
        addKey(città);
        addKey(stato);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Centro centro)
            return equalKeys(centro);
        return false;
    }

    public String getCittà() {
        return (String) keys[0];
    }

    public String getStato() {
        return (String) keys[1];
    }
}
