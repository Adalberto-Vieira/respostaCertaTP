/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.respostaCerta.model.dao;

import br.cefetmg.respostaCerta.model.domain.Forum;
import br.cefetmg.respostaCerta.model.domain.Forum;
import br.cefetmg.respostaCerta.model.exception.PersistenceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author umcan
 */
public class ForumDAOImpl implements ForumDAO{
    
    private static ForumDAOImpl forumDAO = null;        

    private static final HashMap<Long, Forum> forumDB = new HashMap<>();    
    private static long forumCount;
    
    public ForumDAOImpl() { 
        forumCount = 0;
    }

    public static ForumDAOImpl getInstance() {
        
        if (forumDAO == null)
            forumDAO = new ForumDAOImpl();
        
        return  forumDAO;
    }
    
    @Override
    synchronized public void insert(Forum forum) throws PersistenceException {

        if (forum == null)
            throw new PersistenceException("Entidade não pode ser nula.");                
        
        Long forumId = forum.getQuestao().getIdQuestao();
        
        if ((forumId != null) && forumDB.containsKey(forumId))
            throw new PersistenceException("Duplicação de chave.");
        
        forumId = ++forumCount;
        forum.getQuestao().setIdQuestao(forumId);
        forumDB.put(forumId, forum);
    }
    
    @Override
    synchronized public void update(Forum forum) throws PersistenceException {

        if (forum == null)
            throw new PersistenceException("Entidade não pode ser nula.");              
        
        Long forumId = forum.getQuestao().getIdQuestao();

        if (forumId == null)
            throw new PersistenceException("Chave da entidade não pode ser nulo.");        
        
        if (!forumDB.containsKey(forumId))
            throw new PersistenceException("Não existe entidade com a chave " + forumId + ".");
        
        forumDB.replace(forumId, forum);
    }

    @Override
    synchronized public Forum delete(Long forumId) throws PersistenceException {
        if (forumId == null)
            throw new PersistenceException("Chave da entidade não pode ser nulo.");
        
        if (!forumDB.containsKey(forumId))
            throw new PersistenceException("Não existe entidade com a chave " + forumId + ".");
        
        return forumDB.remove(forumId);
    }

    @Override
    public Forum getForumById(Long forumId) throws PersistenceException {
        
        if (forumId == null)
            throw new PersistenceException("Chave da entidade não pode ser nulo.");
        
        if (!forumDB.containsKey(forumId))
            throw new PersistenceException("Não existe entidade com a chave " + forumId + ".");
        
        return forumDB.get(forumId);        
        
    }

    @Override
    public List<Forum> listAll() throws PersistenceException {
        List<Forum> forumList = new ArrayList<>();
        
        Iterator<Forum> iterator = forumDB.values().iterator();
	while (iterator.hasNext())
            forumList.add(iterator.next());
        
        return forumList;
    }
    
}