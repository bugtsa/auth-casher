package com.bugtsa.auth.casherauthserver.entity;

import com.bugtsa.auth.casherauthserver.auth.entity.BaseIdEntity
import javax.persistence.Entity;

@Entity
class Permission : BaseIdEntity() {

    var name: String? = null
}
