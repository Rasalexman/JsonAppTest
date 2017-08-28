package mincor.com.jsonapptest.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 25.03.2017.
 */

public class EventDispatcher {
    private static volatile EventDispatcher ourInstance;
    private Map<String, Map<Object, List<String>>> listHashMap = new HashMap<>();
    public static EventDispatcher getInstance(){
        if (ourInstance == null){
            synchronized (EventDispatcher.class) {
                if (ourInstance == null) {
                    ourInstance = new EventDispatcher();
                }
            }
        }
        return ourInstance;
    }
    private EventDispatcher() {}

    /**
     * Добавляем прослушиватель события на текущий объект
     * @param event - название события
     * @param o - объект у которого будет вызван коллбэк
     * @param funName - название функции (ONLY PUBLIC METHODS) коллбэка с параметрами String event, Object data
     * @return
     */
    public static boolean addEventListener(String event, Object o, String funName){

        if(!getInstance().listHashMap.containsKey(event)){
            getInstance().listHashMap.put(event, new HashMap<Object, List<String>>());
        }
        Map<Object, List<String>> eventsListeners = getInstance().listHashMap.get(event);
        if(!eventsListeners.containsKey(o)){
            eventsListeners.put(o, new ArrayList<String>());
        }
        List<String> allCalbacks = eventsListeners.get(o);
        boolean isAddedAlready = getInstance().hasCallback(funName, allCalbacks);
        if(!isAddedAlready){
            allCalbacks.add(funName);
        }
        //L.d("EVENT = "+event+" isAdded = "+isAddedAlready);
        return isAddedAlready;
    }

    public static boolean removeEventListener(String event, Object o, String funName){
        boolean isRemoved = false;
        if(getInstance().listHashMap.containsKey(event)){
            Map<Object, List<String>> eventsListeners = getInstance().listHashMap.get(event);
            if(eventsListeners.containsKey(o)){
                List<String> allCalbacks = eventsListeners.get(o);
                for(String callback:allCalbacks) {
                    if(callback.equals(funName)){
                        allCalbacks.remove(callback);
                        isRemoved = true;
                        break;
                    }
                }
                // если не осталось больше слушателей удаляем все события
                if(allCalbacks.size() == 0){
                    eventsListeners.remove(o);
                }
                if(eventsListeners.size() == 0){
                    getInstance().listHashMap.remove(event);
                }
            }
        }
        return isRemoved;
    }

    public static boolean removeAllListenersFromEvent(String event){
        boolean isRemovedAll = false;
        if(getInstance().listHashMap.containsKey(event)) {
            Map<Object, List<String>> eventsListeners = getInstance().listHashMap.get(event);
            for (Map.Entry<Object, List<String>> entry:eventsListeners.entrySet()) {
                entry.getValue().clear();
            }
            eventsListeners.clear();
            getInstance().listHashMap.remove(event);
            isRemovedAll = true;
        }
        return isRemovedAll;
    }

    public static void dispatchEvent(String event, Object data){
        if(getInstance().listHashMap.containsKey(event)){
            Map<Object, List<String>> eventsListeners = getInstance().listHashMap.get(event);
            List<String> allCalbacks;
            Object keyObject;
            for (Map.Entry<Object, List<String>> entry : eventsListeners.entrySet()) {
                allCalbacks = entry.getValue();
                keyObject = entry.getKey();
                if(allCalbacks.size() > 0){
                    for (String callback : allCalbacks){
                        try {
                            final Method method = keyObject.getClass().getDeclaredMethod(callback, String.class, Object.class);
                            method.invoke(keyObject, event, data);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private boolean hasCallback(String inputEvent, List<String> callbacks){
        boolean isHasCallback = false;
        for (String callback : callbacks){
            if(callback.equals(inputEvent)){
                isHasCallback = true;
                break;
            }
        }
        return isHasCallback;
    }
}
