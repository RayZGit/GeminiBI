package com.rayzhou.springboottemplate.model.dto.user;

import com.rayzhou.springboottemplate.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private String userName;

    private String userProfile;

    private String userRole;

    private static final long serialVersionUID = 1L;
}