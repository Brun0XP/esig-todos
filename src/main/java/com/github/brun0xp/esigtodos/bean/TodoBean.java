package com.github.brun0xp.esigtodos.bean;

import com.github.brun0xp.esigtodos.dao.TodoDAO;
import com.github.brun0xp.esigtodos.entity.Todo;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class TodoBean extends CrudBean<Todo, TodoDAO> {

    private Filter filter = Filter.ALL;
    private TodoDAO todoDAO;

    @Override
    public TodoDAO getDAO() {
        if (todoDAO == null) todoDAO = new TodoDAO();
        return todoDAO;
    }

    @Override
    public void save() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        getEntity().setIp(ipAddress);

        super.save();
    }

    @Override
    public void read() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        super.read();

        String finalIpAddress = ipAddress;
        setEntities(getEntities().stream().filter(todo ->
                todo.getIp().equals(finalIpAddress)
        ).collect(Collectors.toList()));
    }

    @Override
    public Todo createNewEntity() {
        return new Todo();
    }

    public void clearDoneTodos() {
        getDoneTodos().forEach(this::delete);
    }

    public List<Todo> getPendingTodos() {
        return getEntities().stream().filter(entity ->
                !entity.isDone()
        ).collect(Collectors.toList());
    }

    public List<Todo> getDoneTodos() {
        return getEntities().stream().filter(
                Todo::isDone
        ).collect(Collectors.toList());
    }

    public List<Todo> getFilteredTodos() {
        if (isShowingAll()) return getEntities();
        else if (isShowingPending()) return getPendingTodos();
        else if (isShowingDone()) return getDoneTodos();
        else return null;
    }

    public void setFilterAll() {
        filter = Filter.ALL;
    }
    public void setFilterPending() {
        filter = Filter.PENDING;
    }
    public void setFilterDone() {
        filter = Filter.DONE;
    }
    public boolean isShowingAll() {
        return filter.equals(Filter.ALL);
    }
    public boolean isShowingPending() {
        return filter.equals(Filter.PENDING);
    }
    public boolean isShowingDone() {
        return filter.equals(Filter.DONE);
    }

    enum Filter {
        ALL, PENDING, DONE
    }
}
