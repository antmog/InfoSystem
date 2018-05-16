package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class SetNewStatusDto implements Serializable {

    @NotNull
    private int entityId;

    @Pattern(regexp="^(ACTIVE|BLOCKED|INACTIVE)$",message="invalid code")
    private String entityStatus;
}
