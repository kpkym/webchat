package com.ou.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author kpkym
 * Date: 2018-10-12 20:06
 */
// @Repository
public class MessageDAO {
    @Autowired
    MongoTemplate mongoTemplate;
}
