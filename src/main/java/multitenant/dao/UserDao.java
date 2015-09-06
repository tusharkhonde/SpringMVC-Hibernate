package multitenant.dao;

import multitenant.models.*;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.annotations.QueryHints;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TUSHAR_SK on 8/1/15.
 */

@Repository
@Transactional
public class UserDao {
        /**
         * Save the user in the database.
         */
        public void createUser(User user, Tenant tenant) {
            user.setTenant(tenant);
            tenant.setUser(user);
            entityManager.persist(user);
            entityManager.persist(tenant);
        }

        public void createStory(Story story, String userId) throws ParseException {

            List<Story> s = new ArrayList<>();
            s.add(story);
            Project p  = getPById(new Long(userId));
            p.setStory(s);
            story.setProject(p);
            entityManager.persist(story);

        }

        public void createCard(Card card, String userId) throws ParseException {

            List<Card> c = new ArrayList<>();
            c.add(card);
            Project p  = getPById(new Long(userId));
            p.setCard(c);
            card.setProject(p);
            entityManager.persist(card);

        }

        public void createTask(Task task, String userId) throws ParseException {

            List<Task> t = new ArrayList<>();
            t.add(task);
            Project p  = getPById(new Long(userId));
            p.setTask(t);
            task.setProject(p);
            entityManager.persist(task);

        }

        public void createProject(Project project, String userId) throws ParseException {

            User u = getById(new Long(userId));
            u.setProject(project);
            project.setUser(u);
            entityManager.persist(project);


       }
        /**
         * Delete the user from the database.
         */
        public void delete(User user) {
            if (entityManager.contains(user))
                entityManager.remove(user);
            else
                entityManager.remove(entityManager.merge(user));
            return;
        }

        /**
         * Return all the users stored in the database.
         */

        public List getAll() {
            return entityManager.createQuery("from User").getResultList();
        }



        /**
         * Return the user having the passed id.
         */

        public Project getPById(long id){
            return entityManager.find(Project.class, id);
        }

        public User getById(long id) {
            return entityManager.find(User.class, id);
        }

        public List getUserById(Long id) {
            Query q = entityManager.createQuery("from User u where u.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        }

        public List getByEmail(String email){
             Query q = entityManager.createQuery("from User u where u.email = :email");
             q.setParameter("email",email);
             return q.getResultList();
        }

        public List getProjectById(Long Id){
            String queryString = "from Project p where p.id =  :Id";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("Id", Id);
            return query.getResultList();
        }

        public List getStoryById(Long Id){
            String queryString = "from Story s where s.storyId =  :Id";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("Id", Id);
            return query.getResultList();
        }
        public List getCardById(Long Id){
            String queryString = "from Card c where c.cardId =  :Id";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("Id", Id);
            return query.getResultList();
        }
        public List getTaskById(Long Id){
            String queryString = "from Task t where t.taskId =  :Id";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("Id", Id);
            return query.getResultList();
        }

        public void updateStory(Story story , long Id) {
            Story s = entityManager.find(Story.class, Id);
            s.setStoryTitle(story.getStoryTitle());
            s.setStoryDescription(story.getStoryDescription());
            s.setTotalHours(story.getTotalHours());
            s.setRemainingHours(story.getRemainingHours());
            s.setAssignedTo(story.getAssignedTo());
            entityManager.persist(s);
        }

        public void updateTask(Task task, long Id){
            Task t = entityManager.find(Task.class, Id);
            t.setTaskName(task.getTaskName());
            t.setTaskDescription(task.getTaskDescription());
            t.setStartdate(task.getStartdate());
            t.setFinishdate(task.getFinishdate());
            t.setTasktype(task.getTasktype());
            t.setAssignedTo(task.getAssignedTo());
            entityManager.persist(t);
        }

        public void updateCard(Card card, long Id){
            Card c = entityManager.find(Card.class,Id);
            c.setCardName(card.getCardName());
            c.setCardDescription(card.getCardDescription());
            c.setCardType(card.getCardType());
            c.setAssignedTo(card.getAssignedTo());
            entityManager.persist(c);
        }

        public void deleteType(String type, long Id){
            if (type.contentEquals("story")) {
                Story s = entityManager.find(Story.class, Id);
                entityManager.remove(s);
                entityManager.flush();
            } else if (type.contentEquals("cards")) {
                Card c = entityManager.find(Card.class,Id);
                entityManager.remove(c);
                entityManager.flush();
            } else if (type.contentEquals("tasks")) {
                Task t = entityManager.find(Task.class, Id);
                entityManager.remove(t);
                entityManager.flush();
            }
        }
        /** Check if user details are valid or not.
             *
             * @param Id
             * @param pass
             * @return
        */
         public List getByUserPass(long Id, String pass){
            String queryString = "from User u where u.id =  :Id and u.password = :pass";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("Id", Id);
            query.setParameter("pass", pass);
            return query.getResultList();
        }

         /** Get Tasks.
         *
         * @param id
         * @return
         */
        public List<Task> getTasksById(long id) {
             Query query = entityManager.createQuery("from Task t where t.project.id = :id");
             query.setParameter("id",id);
             return query.getResultList();
        }

        /** Get Stories.
         *
         * @param id
         * @return
         */
        public List<Story> getStorysById(long id) {
            Query query = entityManager.createQuery("from Story s where s.project.id = :id");
            query.setParameter("id",id);
            return query.getResultList();
        }

        /** Get Cards.
         *
         * @param id
         * @return
         */
        public List<Card> getCardsById(long id) {
            Query query = entityManager.createQuery("from Card c where c.project.id = :id");
            query.setParameter("id", id);
            return query.getResultList();
        }


        /**
         * Update the passed user in the database.
         */
        public void update(User user) {
            entityManager.merge(user);
            return;
        }

        public List cardCount(Long userId) throws SQLException {

            Query query = entityManager.createQuery("SELECT c.cardType ,COUNT(*) as count FROM Card c where c.project.id = :id GROUP BY c.cardType ORDER BY c.cardType desc");
            query.setParameter("id",userId);
            return query.getResultList();

        }

        public List taskCount(Long userId) throws SQLException {

            Query query = entityManager.createQuery("SELECT t.taskType ,COUNT(*) as count FROM Task t where t.project.id = :id GROUP BY t.taskType ORDER BY t.taskType desc");
            query.setParameter("id",userId);
            return query.getResultList();

        }

        public List tCount(Long userId) {
            Query query = entityManager.createQuery(" select SUM(s.totalHours) as total FROM Story s where s.project.id = :id");
            query.setParameter("id",userId);
            return query.getResultList();
        }

        public List rCount(Long userId) {
            Query query = entityManager.createQuery(" select SUM(s.remainingHours) as total FROM Story s where s.project.id = :id");
            query.setParameter("id",userId);
            return query.getResultList();
        }

        // An EntityManager will be automatically injected from entityManagerFactory
        // setup on DatabaseConfig class.
        @PersistenceContext
        private EntityManager entityManager;

} // class UserDao

