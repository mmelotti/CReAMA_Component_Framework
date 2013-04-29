package my_components.binomio;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table BINOMIO.
 */
public class Binomio extends com.example.my_fragment.ComponentSimpleModel  {

    private Long id;
    private Long targetId;
    private Integer fechada;
    private Integer aberta;
    private Integer simples;
    private Integer complexa;
    private Integer vertical;
    private Integer horizontal;
    private Integer simetrica;
    private Integer assimetrica;
    private Integer opaca;
    private Integer translucida;

    public Binomio() {
    }

    public Binomio(Long id) {
        this.id = id;
    }

    public Binomio(Long id, Long targetId, Integer fechada, Integer aberta, Integer simples, Integer complexa, Integer vertical, Integer horizontal, Integer simetrica, Integer assimetrica, Integer opaca, Integer translucida) {
        this.id = id;
        this.targetId = targetId;
        this.fechada = fechada;
        this.aberta = aberta;
        this.simples = simples;
        this.complexa = complexa;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.simetrica = simetrica;
        this.assimetrica = assimetrica;
        this.opaca = opaca;
        this.translucida = translucida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getFechada() {
        return fechada;
    }

    public void setFechada(Integer fechada) {
        this.fechada = fechada;
    }

    public Integer getAberta() {
        return aberta;
    }

    public void setAberta(Integer aberta) {
        this.aberta = aberta;
    }

    public Integer getSimples() {
        return simples;
    }

    public void setSimples(Integer simples) {
        this.simples = simples;
    }

    public Integer getComplexa() {
        return complexa;
    }

    public void setComplexa(Integer complexa) {
        this.complexa = complexa;
    }

    public Integer getVertical() {
        return vertical;
    }

    public void setVertical(Integer vertical) {
        this.vertical = vertical;
    }

    public Integer getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Integer horizontal) {
        this.horizontal = horizontal;
    }

    public Integer getSimetrica() {
        return simetrica;
    }

    public void setSimetrica(Integer simetrica) {
        this.simetrica = simetrica;
    }

    public Integer getAssimetrica() {
        return assimetrica;
    }

    public void setAssimetrica(Integer assimetrica) {
        this.assimetrica = assimetrica;
    }

    public Integer getOpaca() {
        return opaca;
    }

    public void setOpaca(Integer opaca) {
        this.opaca = opaca;
    }

    public Integer getTranslucida() {
        return translucida;
    }

    public void setTranslucida(Integer translucida) {
        this.translucida = translucida;
    }

}