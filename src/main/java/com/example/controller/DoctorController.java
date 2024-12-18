package com.example.controller;

import com.example.common.Result;
import com.example.entity.Doctor;
import com.example.service.DoctorService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 医生前端操作接口
 **/
@Api(tags = "医生前端操作接口")
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Doctor doctor) {
        doctorService.add(doctor);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        doctorService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        doctorService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Doctor doctor) {
        doctorService.updateById(doctor);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Doctor doctor = doctorService.selectById(id);
        return Result.success(doctor);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Doctor doctor ) {
        List<Doctor> list = doctorService.selectAll(doctor);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Doctor doctor,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Doctor> page = doctorService.selectPage(doctor, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/selectPage2")
    public Result selectPage2(Doctor doctor,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Doctor> page = doctorService.selectPage2(doctor, pageNum, pageSize);
        return Result.success(page);
    }
}