package multitenant.controller;

import multitenant.models.*;
import multitenant.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.String;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;


@Controller
public class MainController {

    @ModelAttribute("project")
    public Project createProjectModel(){
        return new Project();
    }

    // Default Route for login page
    @RequestMapping(value="/")
    public String index() {
        return "Login";
    }

    // Verify user credentials
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(@RequestParam("user")Long user, @RequestParam("pass")String pass, ModelMap model) {
        try {
            List<User> l = userDao.getByUserPass(user, pass);
            User u = l.get(0);
            Long userid = u.getId();
            String tType = u.getTenanttype();
           if(tType.contentEquals("kanban")){
                List<Card> card = userDao.getCardsById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("cards",card);
            }
            else if (tType.contentEquals("scrum")){
                List<Story> story = userDao.getStorysById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("story",story);
            }
            else if(tType.contentEquals("waterfall")){
                List<Task> task = userDao.getTasksById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("tasks",task);
            }
        }
        catch (Exception e){
            return "Login";
        }
        return "Dashboard";
    }

    // Redirect to User Dashboard
    @RequestMapping(value="/{userid}", method = RequestMethod.GET)
    public String login(@PathVariable Long userid, ModelMap model) {
        try {
            List<User> l = userDao.getUserById(userid);
            User u = l.get(0);
            String tType = u.getTenanttype();
            if(tType.contentEquals("kanban")){
                List<Card> card = userDao.getCardsById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("cards",card);
            }
            else if (tType.contentEquals("scrum")){
                List<Story> story = userDao.getStorysById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("story",story);
            }
            else if(tType.contentEquals("waterfall")){
                List<Task> task = userDao.getTasksById(userid);
                model.addAttribute("userid",userid);
                model.addAttribute("tenant", tType);
                model.addAttribute("tasks",task);
            }
        }
        catch (Exception e){
            return "Login";
        }
        return "Dashboard";
    }

    // Create New user
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") User user, ModelMap model) {
        String tenant = user.getTenanttype();
        if(tenant.contentEquals("kanban")){
            Tenant t = new Tenant("Card Name", "Card Description", "Card Type", "Assigned To");
            userDao.createUser(user, t);
        }
        else if(tenant.contentEquals("scrum")){
            Tenant t = new Tenant("Story Title", "Story Description", "Total Hours", "Remaining Hours", "Assigned To");
            userDao.createUser(user, t);
        }
        else if(tenant.contentEquals("waterfall")){
            Tenant t = new Tenant("Task Name", "Task Description", "Start Date", "Finish Date", "Assigned To");
            userDao.createUser(user, t);
        }
        List u = userDao.getByEmail(user.getEmail());
        User us  = (User) u.get(0);
        model.addAttribute("tenant", tenant);
        model.addAttribute("userId",us.getId());
        return "Project";
    }

    // Create New Project
    @RequestMapping(value = "/{userId}/project/{tType}", method = RequestMethod.POST)
    public String createProject(@ModelAttribute("project") Project project, BindingResult result, ModelMap model, @PathVariable String userId, @PathVariable String tType) throws ParseException{
     userDao.createProject(project, userId);

            if(tType.contentEquals("kanban")){
                List<Card> card = new ArrayList<>();
                model.addAttribute("tenant", tType);
                model.addAttribute("cards",card);
            }
            else if (tType.contentEquals("scrum")){
                List<Story> story = new ArrayList<>();
                model.addAttribute("tenant", tType);
                model.addAttribute("story",story);
            }
            else if(tType.contentEquals("waterfall")){
                List<Task> task = new ArrayList<>();
                model.addAttribute("tenant", tType);
                model.addAttribute("tasks",task);
            }
        model.addAttribute("userid",userId);
        return "Dashboard";
    }

    // Redirect to Add page to create new Task/Card/Story
    @RequestMapping(value = "/{userId}/add/{type}", method = RequestMethod.GET)
    public String getAdd(@ModelAttribute("project") Project project, @PathVariable Long userId, @PathVariable String type, ModelMap model) {

        List p = userDao.getProjectById(userId);
        Project ps  = (Project) p.get(0);
        model.addAttribute("project",ps.getProjectname());
        model.addAttribute("type", type);
        model.addAttribute("userid",userId);
        return "Add";
    }

    // Create new Task/Card/Story
    @RequestMapping(value = "/{userId}/add/{type}", method = RequestMethod.POST)
    public ModelAndView addType(@ModelAttribute("story") Story story, @ModelAttribute("card") Card card, @ModelAttribute("task") Task task, @PathVariable String userId, @PathVariable String type, ModelMap model) throws ParseException {
        if(type.contentEquals("story")){
            userDao.createStory(story, userId);
        }
        else if(type.contentEquals("cards")){
            userDao.createCard(card, userId);
        }
        else if(type.contentEquals("tasks")){
            userDao.createTask(task, userId);
        }

        return new ModelAndView("redirect:" + "/"+userId);
    }

    // Get details of Task/Card/Story
    @RequestMapping(value = "/{userId}/edit/{type}/{typeId}", method = RequestMethod.GET)
    public String getTypeDetails(@ModelAttribute("project") Project project, @PathVariable Long userId, @PathVariable String type, @PathVariable Long typeId, ModelMap model) {

        if(type.contentEquals("story")){
            List ls = userDao.getStoryById(typeId);
            Story s = (Story) ls.get(0);
            model.addAttribute("story", s);
        }
        else if(type.contentEquals("cards")){
            List lc = userDao.getCardById(typeId);
            Card c = (Card) lc.get(0);
            model.addAttribute("cards", c);
        }
        else if(type.contentEquals("tasks")){
            List lt = userDao.getTaskById(typeId);
            Task t = (Task) lt.get(0);
            model.addAttribute("tasks", t);
        }

        List p = userDao.getProjectById(userId);
        Project ps  = (Project) p.get(0);
        model.addAttribute("project",ps.getProjectname());
        model.addAttribute("type", type);
        model.addAttribute("userid",userId);
        return "Details";
    }

    // Update Task/Card/Story
    @RequestMapping(value = "/{userId}/edit/{type}/{typeId}", method = RequestMethod.POST)
    public ModelAndView updateType(@ModelAttribute("story") Story story, @ModelAttribute("card") Card card, @ModelAttribute("task") Task task,
                                   @PathVariable String userId, @PathVariable String type,@PathVariable Long typeId) throws ParseException {
        if (type.contentEquals("story")) {
            userDao.updateStory(story, typeId);
        } else if (type.contentEquals("cards")) {
            userDao.updateCard(card, typeId);
        } else if (type.contentEquals("tasks")) {
            userDao.updateTask(task, typeId);
        }

        return new ModelAndView("redirect:" + "/" + userId);
    }

    // Delete Task/Card/Story
    @RequestMapping(value = "/{userId}/delete/{type}/{typeId}", method = RequestMethod.GET)
    public ModelAndView deleteType(@PathVariable String userId, @PathVariable String type,@PathVariable Long typeId){
        userDao.deleteType(type,typeId);
        return new ModelAndView("redirect:" + "/" + userId);
    }

    // Get project status
    @RequestMapping(value = "/{userId}/status", method = RequestMethod.GET)
    public String showStatus(@PathVariable Long userId, ModelMap model) throws SQLException{
        User u  = (User)userDao.getUserById(userId).get(0);
        Project p = (Project)userDao.getProjectById(userId).get(0);

        if (u.getTenanttype().contentEquals("kanban")) {
            List l = userDao.cardCount(userId);
            List<Object[]> resultList = l;
            Map<String, Integer> resultMap = new HashMap<>(resultList.size());
            int a[] = new int[4];
            int i = 0;

            for (Object[] result : resultList) {
                String name = (String) result[0];
                int count = ((Number) result[1]).intValue();
                a[i] = count;
                i++;
                resultMap.put(name, count);
            }
            model.addAttribute("a", a);
        }
        else if (u.getTenanttype().contentEquals("scrum")){

            List<Long> t = userDao.tCount(userId);
            List<Long> r = userDao.rCount(userId);

            Long tr =  t.get(0);
            Long rr =  r.get(0);

            int tHours = Long.valueOf(tr).intValue();
            int rHours = Long.valueOf(rr).intValue();

            List<Integer> actual = new ArrayList<>();
            List<Integer> initial = new ArrayList<>();

            int burnDown = 2 * 5;

            while(rHours > 0){
                actual.add(rHours);
                rHours = rHours - burnDown;
            }

            while (tHours >0){
                initial.add(tHours);
                tHours = tHours - burnDown;
            }
            int k = 0;
            int l = 1;

            List<Integer[]> dataArray = new ArrayList<>();
            for(int item : actual){
                Integer[] o = {k,item,initial.get(k)};
                dataArray.add(o);
                k++;
            }

            if(initial.size() > actual.size()){
                for(int item : initial){
                    if(l > actual.size()){
                        Integer[] o = {l,0,item};
                        dataArray.add(o);
                    }
                    l++;
                }
                Integer[] o = {l,0,0};
                dataArray.add(o);
            }
            else if(initial.size() < actual.size()){
                for(int item : initial){
                    if(l > initial.size()){
                        Integer[] o = {l,item,0};
                        dataArray.add(o);
                    }
                    l++;
                }
                Integer[] o = {l,0,0};
                dataArray.add(o);
            }
            else if(initial.size() == actual.size()){
                Integer[] o = {k,0,0};
                dataArray.add(o);
            }
            model.addAttribute("dataArray",dataArray);
        }

        else if (u.getTenanttype().contentEquals("waterfall")){

            List l = userDao.taskCount(userId);
            List<Object[]> resultList = l;
            Map<String, Integer> resultMap = new HashMap<>(resultList.size());
            int a[] = {0,0,0};

            for (Object[] result : resultList) {
                String name = (String) result[0];
                int count = ((Number) result[1]).intValue();
                if(name.contentEquals("Requested")) {
                    a[0] = count;
                }
                else if (name.contentEquals("In Progress")) {
                    a[1] = count;
                }
                else if (name.contentEquals("Completed")) {
                    a[2] = count;
                }
                resultMap.put(name, count);
            }
            model.addAttribute("a", a);
        }

        model.addAttribute("tenant",u.getTenanttype());
        model.addAttribute("project",p.getProjectname());
        model.addAttribute("userid",userId);

        return "Status";
    }


    // Get User
    @RequestMapping(value="/get")
    @ResponseBody
    public String getById(long id) {
        String userId;
        try {
            User user = userDao.getById(id);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "User not found: " + ex.toString();
        }
        return "The user id is: " + userId;
    }

    @Autowired
    private UserDao userDao;


}