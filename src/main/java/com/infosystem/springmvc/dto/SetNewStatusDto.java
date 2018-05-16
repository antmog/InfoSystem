package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class SetNewStatusDto implements Serializable {
    @NotNull
    private int entityId;
    @Enumerated(EnumType.STRING)
    private Status entityStatus;
}
