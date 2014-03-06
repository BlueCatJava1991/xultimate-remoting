package org.danielli.xultimate.remoting.web;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.remoting.dto.Account;
import org.danielli.xultimate.remoting.dubbo.serialize.Consumer;
import org.danielli.xultimate.remoting.dubbo.serialize.Provider;
import org.danielli.xultimate.remoting.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 客户端和服务端都在一个jvm实例上，是有问题的。看测试。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Consumer
 * @see Provider
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {
	
	@Resource(name = "remotingDubboAccountService")
	public AccountService accountService;
	
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String toInsertAccount() {
		return "account_add";
	}
	
	@RequestMapping(method = { RequestMethod.POST })
	public String doInsertAccount(Account account, ModelMap modelMap) {
		accountService.insertAccount(account);
		modelMap.put("name", account.getName());
		return "redirect:/accounts";
	}
	
	@RequestMapping(method = { RequestMethod.GET })
	public String getAccounts(Account account, ModelMap modelMap) {
		List<Account> accountList = null;
		if (account == null || account.getName() == null) {
			accountList = accountService.getAllAccounts();
		} else {
			accountList = accountService.getAccounts(account.getName());
		}
		modelMap.put("accounts", accountList);
		return "account_list";
	}
}
