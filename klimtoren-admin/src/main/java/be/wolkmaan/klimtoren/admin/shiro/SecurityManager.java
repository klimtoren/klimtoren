/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.admin.shiro;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 *
 * @author Nariman
 */
public class SecurityManager extends DefaultWebSecurityManager{

    public SecurityManager(){
        super();
    }

    public void setActiveSessionsCacheName(String activeSessionsCacheName) {
        SessionManager sessionManager = this.getSessionManager();
        ((CachingSessionDAO)((DefaultSessionManager)sessionManager).getSessionDAO()).setActiveSessionsCacheName(activeSessionsCacheName);
    }

}
