package org.springframework.uac.core.component;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.uac.model.domain.GzPermission;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.perm.GzRolePermService;
import org.springframework.uac.utils.MessageSourceUtil;
import org.springframework.uac.utils.enums.PermTypeEnum;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author mayato
 */
@Configuration
public class InitPermission {
    @Autowired
    ConfigurableApplicationContext context;
    @Autowired
    GzPermissionService gzPermissionService;
    @Autowired
    GzRolePermService gzRolePermService;
    @Autowired
    MessageSourceUtil sourceUtil;
    @Autowired
    InitRole initRole;

    @Value("${server.servlet.context-path}")
    private String path;

    /**
     * 初始化
     */
    public void init() {

        //用来存储创建的权限，用于批量插入
        List<GzPermission> list = new LinkedList<>();
        //初始化的大类
        Stream.of(PermTypeEnum.values()).forEach(item->creatGzPermissionType(list,item.getId(), sourceUtil.message(item.getId()), item.getRoleType()));
        String[] controllerNames = context.getBeanNamesForAnnotation(RestController.class);
        for (String controllerName : controllerNames) {
            Object bean = context.getBean(controllerName);
            Class<?> clazz = bean.getClass();

            IRequestMapping controllerMapping = AnnotationUtils.findAnnotation(clazz,IRequestMapping.class);

            if (null != controllerMapping) {
                //获取controller的前缀
                String[] controllerValue = controllerMapping.value();
                int[] roleTypeids=controllerMapping.roleType();
                String component=controllerMapping.component();
                int controllerId = controllerMapping.id();
                String controlName;
                if (StringUtils.isNotBlank(controllerMapping.name())) {
                    controlName = controllerMapping.name();
                } else {
                    controlName = sourceUtil.message(controllerId);
                }
                int[] parentIds = controllerMapping.parentIds();
                String prefix = null;
                String roleType=null;
                int parentId=0;
                if(ArrayUtils.isNotEmpty(controllerValue)){
                    prefix = path+controllerValue[0];
                }
                if(ArrayUtils.isNotEmpty(roleTypeids)){
                    roleType=intArrayJoin(roleTypeids);
                }
                if(ArrayUtils.isNotEmpty(parentIds)){
                    parentId=parentIds[0];
                }

//                if(ArrayUtils.isNotEmpty(roleTypeids)){
//                    roleType=intArrayJoin(roleTypeids);
//                }
                //创建controller权限
                creatGzPermission(list,controllerId, controlName, 1, prefix, null, parentId, String.valueOf(parentId),roleType, component, controllerMapping.isShow());

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    //获取方法头上的注解
                    IRequestMapping methodMapping = AnnotationUtils.findAnnotation(method, IRequestMapping.class);
                    if (null != methodMapping) {
                        String[] methodValue = methodMapping.value();
                        int methodId = methodMapping.id();

                        String methodName;
                        if (StringUtils.isNotBlank(controllerMapping.name())) {
                            methodName = methodMapping.name();
                        } else {
                            methodName = sourceUtil.message(methodId);
                        }

                        int[] methodParentIds=methodMapping.parentIds();
                        int[] methodroleTypeIds = methodMapping.roleType();
                        String methodRoleType=null;
                        String methodParentIdStr=null;
                        String methodUrl = null;
                        if (ArrayUtils.isNotEmpty(methodValue)) {
                            //获取url
                            methodUrl = methodValue[0];
                        }
                        //角色类型
                        if(ArrayUtils.isNotEmpty(methodroleTypeIds)){
                            methodRoleType=intArrayJoin(methodroleTypeIds);
                        }else {
                            methodRoleType=roleType;
                        }
                        //角色祖先
                        if(ArrayUtils.isNotEmpty(methodParentIds)){
                            methodParentIdStr=intArrayJoin(methodParentIds);
                        }else {
                            methodParentIdStr=parentId+","+controllerId;
                        }
                        //创建method权限
                        creatGzPermission(list,methodId, methodName, 2, prefix, prefix+methodUrl, controllerId, methodParentIdStr,methodRoleType);
                    }

                }
            }

        }

        //删除id 100000以下的权限，重新生成
        gzPermissionService.saveOrUpdateBatch(list);
    }
    private void creatGzPermissionType(List<GzPermission> list,Integer id,String name,String roleType){
        creatGzPermission(list,id,name,0,null,null,null,null,roleType);
    }


    private void creatGzPermission(List<GzPermission> list,Integer id,String name,Integer type,String prefix,String url,Integer parentId,String parentIds,String roleType, boolean isShow) {
        creatGzPermission(list, id, name, type, prefix, url, parentId, parentIds, roleType, null, isShow);
    }

    private void creatGzPermission(List<GzPermission> list,Integer id,String name,Integer type,String prefix,String url,Integer parentId,String parentIds,String roleType) {
        creatGzPermission(list, id, name, type, prefix, url, parentId, parentIds, roleType, null, true);
    }
    private void creatGzPermission(List<GzPermission> list,Integer id,String name,Integer type,String prefix,String url,Integer parentId,String parentIds,String roleType, String component, boolean isShow) {
        GzPermission permission = new GzPermission();
        permission.setPermCreateBy("system");
        permission.setPermCreateDate(LocalDateTime.now());
        permission.setPermUpdateBy("system");
        permission.setPermUpdateDate(LocalDateTime.now());
        permission.setPermId(id);
        permission.setPermName(name);
        permission.setPermParentId(parentId);
        permission.setPermParentIds(parentIds);
        permission.setComponent(component);
        permission.setPermUrl(url);
        permission.setPermUrlPrefix(prefix);
        permission.setPermStatus(true);
        permission.setPermIsShow(isShow);
        permission.setPermType(type);
        permission.setPermRoleType(roleType);
        list.add(permission);
//        gzPermissionService.saveOrUpdate(permission);
//        GzRolePerm perm = new GzRolePerm();
//        perm.setPermId(id);
//        perm.setRoleId("0ab7f140-f883-4b11-9f54-9cd0e38c8dcf");
//        gzRolePermService.save(perm);
    }

    private String intArrayJoin(int[] array){
        StringBuffer buffer = new StringBuffer();
        for (int i:array){
            buffer.append(i);
            buffer.append(",");
        }
        if(buffer.length()>0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }
}
