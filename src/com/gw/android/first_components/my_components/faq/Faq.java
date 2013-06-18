package com.gw.android.first_components.my_components.faq;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FAQ.
 */
public class Faq extends com.gw.android.first_components.my_fragment.ComponentSimpleModel  {

    private Long id;
    private Long targetId;
    private Long serverId;
    private String pergunta;
    private String resposta;

    public Faq() {
    }

    public Faq(Long id) {
        this.id = id;
    }

    public Faq(Long id, Long targetId, Long serverId, String pergunta, String resposta) {
        this.id = id;
        this.targetId = targetId;
        this.serverId = serverId;
        this.pergunta = pergunta;
        this.resposta = resposta;
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

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

}
