/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author vk300
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // запази тази антотация и нейните данни, дори когато програмата се изпълнява
public @interface ClassPreamble {
    
    String author();
    
    String date();
    
    int currentRevision() default 1;
    
    String lastModified();
    
    String by();
    
    String[] reviewers();

    
}
