/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.itstepnews1.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 *
 * @author student
 */
@Entity
public class News {
    
	@Id
    public Long id;
	@Index
    public String title;
    public String content;
    
    //private static Long newId = 1L;
    
    public News() {}

    public News(String title, String content) {
        
        //this.id = newId;
        this.title = title;
        this.content = content;
        
        //newId++;
    }
}
