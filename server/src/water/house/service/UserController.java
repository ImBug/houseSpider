package water.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import water.house.entity.HouseInfo;
import water.house.logic.HouseLogic;

@RestController
@RequestMapping("/house")
@Api("userController相关api")
public class UserController {

	@Autowired
	private HouseLogic houseLogic;
    
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
       // @ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
        @ApiImplicitParam(paramType="query",name="name",dataType="String",required=true,value="区域",defaultValue="庭院深深")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getAll",method=RequestMethod.GET)
    public Page<HouseInfo> getUser(@RequestParam("name") String name) {
    	return houseLogic.findAllHouse(null);
    }
    
    
}
