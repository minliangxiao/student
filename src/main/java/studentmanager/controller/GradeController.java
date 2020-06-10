package studentmanager.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studentmanager.page.Page;
import studentmanager.po.Grade;
import studentmanager.service.GradeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/grade")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/gread_list")
    public String list() {

        return "grade/gread_list";
    }

    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            Page page
    ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", gradeService.findList(queryMap));
        ret.put("total", gradeService.getTotal(queryMap));
        return ret;
    }

    /*添加年级*/
    @PostMapping("/add")
    @ResponseBody
    public Map<String, String> add(Grade grade) {
        Map<String, String> ret = new HashMap<String, String>();
        if (StringUtils.isEmpty(grade.getName())) {
            ret.put("type", "error");
            ret.put("msg", "年级名称不能为空");
            return ret;
        }
        if (gradeService.add(grade) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "年级添加失败");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "年级添加成功");
        return ret;
    }

    /*修改年级信息*/
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(Grade grade) {
        Map<String, String> ret = new HashMap<String, String>();
        if (StringUtils.isEmpty(grade.getName())) {
            ret.put("type", "error");
            ret.put("msg", "年级名称不能为空！");
            return ret;
        }
        if (gradeService.edit(grade) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "年级修改失败！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "年级修改成功！");
        return ret;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(
            @RequestParam(value = "ids[]", required = true) Long[] ids
    ) {
        Map<String, String> ret = new HashMap<String, String>();
        if (ids == null || ids.length == 0) {
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的数据！");
            return ret;
        }
        List<String> list = new ArrayList<String>();
        String stringId;
        for (Long id : ids) {
            list.add(id.toString());
        }
        stringId = String.join(",", list);
        /*加这个try。。。catch语句是为了防止有人删除年级下面有班级的数据*/
        try {
            if (gradeService.delete(stringId) <= 0) {
                ret.put("type", "error");
                ret.put("msg", "删除失败！");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "该年级下存在班级信息，请勿冲动！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "年级删除成功！");
        return ret;
    }
}
