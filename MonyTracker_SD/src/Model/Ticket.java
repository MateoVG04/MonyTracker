package Model;

import Model.Strategy.PayBehaviour;

public class Ticket {
    private float prijs;
    private String wieBetaald;
    private String wieOntvangt;
    private PayBehaviour payBehaviour;
    private String tag;
    public Ticket(float prijs, String wieBetaald, String wieOntvangt, PayBehaviour payBehaviour) {
        this.prijs = prijs;
        this.wieBetaald = wieBetaald;
        this.wieOntvangt = wieOntvangt;
        this.payBehaviour = payBehaviour;
    }
    public float getPrijs() {
        return prijs;
    }
    public String getWieBetaald() {
        return wieBetaald;
    }
    public String getWieOntvangt() {
        return wieOntvangt;
    }
    public PayBehaviour getPayBehaviour() {
        return payBehaviour;
    }
}
