package com.ou.mongo;

import com.ou.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author kpkym
 * Date: 2018-10-12 20:06
 */
@Repository
public class MessageDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    public void insert(Message message) {
        mongoTemplate.insert(message);
    }
}
