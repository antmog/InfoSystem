package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.Status;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SetNewStatusDto implements Serializable {
    @NotNull
    private int entityId;
    private Status entityStatus;

    public SetNewStatusDto(int entityId, Status entityStatus) {
        this.entityId = entityId;
        this.entityStatus = entityStatus;
    }

    public SetNewStatusDto() {
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Status getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(Status entityStatus) {
        this.entityStatus = entityStatus;
    }
}
