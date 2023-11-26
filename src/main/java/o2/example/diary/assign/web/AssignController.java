package o2.example.diary.assign.web;


import o2.example.diary.assign.service.AssignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ObjectMapperSupport;
import util.SecurityUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "")
public class AssignController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignController.class);
	@Resource(name = "AssignService")
	private AssignService AssignService;

	// 유저가 입력한 아이디와 비밀번호를 받아서 DB에 있는지 확인한다.
	// 유저가 입력한 아이디와 비밀번호를 받아서 DB에 있는지 확인한다.
	@RequestMapping(produces = "application/json; charset=UTF-8" , value = "/getLoginData" , method = RequestMethod.GET)
	@ResponseBody
	public String getLoginData(HttpServletRequest request,  @RequestParam Map<String, Object> param ) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String password = (String) param.get("password");
			String encryptedPassword = SecurityUtils.encryptPassword(password);
			param.put("password", encryptedPassword);

			List<Map<String, Object>> rData = AssignService.getLoginData(param);
			result.put("SUCCESS", true);
			result.put("RESULT", rData);
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			LOGGER.error(e.getMessage());
			result.put("SUCCESS", false);
			result.put("MSG", e);
		}
		return ObjectMapperSupport.objectToJson(result);
	}

	// 유저가 입력한 회원가입 정보로 회원 정보를 인서트 한다.
	// 유저가 입력한 회원가입 정보로 회원 정보를 인서트 한다.
	@RequestMapping(produces = "application/json; charset=UTF-8" , value = "/insartAssignData" , method = RequestMethod.GET)
	@ResponseBody
	public String insartAssignData(HttpServletRequest request,  @RequestParam Map<String, Object> param ) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String password = (String) param.get("password");
			String encryptedPassword = SecurityUtils.encryptPassword(password);
			param.put("password", encryptedPassword);

			AssignService.insartAssignData(param);
			result.put("SUCCESS", true);
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			LOGGER.error(e.getMessage());
			result.put("SUCCESS", false);
			result.put("MSG", e);
		}
		return ObjectMapperSupport.objectToJson(result);
	}


	@RequestMapping(produces = "application/json; charset=UTF-8" , value = "/chackPhoneNember" , method = RequestMethod.GET)
	@ResponseBody
	public String chackPhoneNember(HttpServletRequest request,  @RequestParam Map<String, Object> param ) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			int rData = AssignService.chackPhoneNember(param);
			result.put("SUCCESS", true);
			result.put("RESULT", rData);

		}catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			result.put("SUCCESS", false);
			result.put("MSG", e);
		}

		return ObjectMapperSupport.objectToJson(result);
	}

	@RequestMapping(produces = "application/json; charset=UTF-8" , value = "/checkPhoneNumber" , method = RequestMethod.GET)
	@ResponseBody
	public String checkPhoneNumber( HttpServletRequest request, @RequestParam Map<String, Object> param ){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int rData = AssignService.chackPhoneNember(param);

			if (rData == 1) {
				result.put("SUCCESS", true);
				result.put("RESULT", rData);
				return ObjectMapperSupport.objectToJson(result);
			} else {
				result.put("SUCCESS", false);
				return ObjectMapperSupport.objectToJson(result);
			}
		}catch(NullPointerException e){
			LOGGER.error(e.getMessage());
			result.put("SUCCESS", false);
			result.put("MSG", e);
			return ObjectMapperSupport.objectToJson(result);
		}
	}

}
