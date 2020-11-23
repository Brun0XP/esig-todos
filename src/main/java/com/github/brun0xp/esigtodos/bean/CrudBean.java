package com.github.brun0xp.esigtodos.bean;

import com.github.brun0xp.esigtodos.dao.CrudDAO;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

public abstract class CrudBean<T, D extends CrudDAO> {

    @Getter @Setter
    private T entity = createNewEntity();

    @Getter @Setter
    private List<T> entities;

    public void save() {
        getDAO().create(entity);
        entity = createNewEntity();
        addMessage("Criado com sucesso!", FacesMessage.SEVERITY_INFO);
    }

    public void update(T entity) {
        getDAO().update(entity);
    }

    public void delete(T entity) {
        getDAO().delete(entity);
        entities.remove(entity);
        addMessage("Deletado com sucesso", FacesMessage.SEVERITY_INFO);
    }

    public void read() {
        entities = getDAO().read();
        if (entities == null || entities.size() < 1) {
            addMessage("Nenhum todo encontrado!", FacesMessage.SEVERITY_WARN);
        }
    }


    public void addMessage(String message, FacesMessage.Severity errorType) {
        FacesMessage facesMessage = new FacesMessage(errorType, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public abstract D getDAO();
    public abstract T createNewEntity();
}
