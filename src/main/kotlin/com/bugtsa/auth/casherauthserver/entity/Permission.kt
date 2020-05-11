package com.bugtsa.auth.casherauthserver.entity;

import javax.persistence.Entity

@Entity
class Permission : BaseIdEntity() {

    var name: String? = null
}
