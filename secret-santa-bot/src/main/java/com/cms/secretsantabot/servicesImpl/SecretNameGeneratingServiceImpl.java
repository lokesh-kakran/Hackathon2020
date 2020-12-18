package com.cms.secretsantabot.servicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.repository.EmployeeRepository;
import com.cms.secretsantabot.services.SecretNameGeneratingService;

@Slf4j
@Service
public class SecretNameGeneratingServiceImpl implements SecretNameGeneratingService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public void saveAllMatchedEmployees() {
		List<Integer> empIdList = getAllEmpId();
		if (!CollectionUtils.isEmpty(empIdList)) {
			generateConnections(empIdList).forEach((fromEmpId,toEmpId) ->{
				Employee employee = employeeRepository.getEmployeesById(fromEmpId);
				employee.setToEmpId(toEmpId);
				employeeRepository.save(employee); //updates the employee
			});
		}
		else{
			log.info("No employee found for the game");
		}
	}

	private Map<Integer, Integer> generateConnections(List<Integer> empList) {
		List<Integer> numbers = new ArrayList<Integer>();
		for(Integer j = 0; j <empList.size(); j++) {
			numbers.add(empList.get(j));
		}
		List<Integer> choices = new ArrayList<Integer>();
		Map<Integer, Integer> fromToMap = new HashMap<>();
		int choice;
		Integer start, temp;
		Random rand = new Random();
		choice = rand.nextInt(numbers.size());
		choices.add(numbers.get(choice));
		start = numbers.get(choice);
		numbers.remove(choice);
		boolean selfLoop = true;
		boolean pickStart = false;
		while(numbers.size() > 2 || pickStart) {
			rand = new Random();
			choice = rand.nextInt(numbers.size());
			choices.add(numbers.get(choice));
			temp = numbers.get(choice);
			numbers.remove(temp);
			if(selfLoop && !pickStart) {
				selfLoop = false;
				numbers.add(start);
			}
			else if (!selfLoop && pickStart) {
				start = temp;
				pickStart = false;
				selfLoop = true;
			}
			else {
				if(temp == start) {
					pickStart = true;
				}
			}
		}
		if(selfLoop && numbers.size() == 2) {
			choice = rand.nextInt(2);
			choices.add(numbers.get(choice));
			choices.add(numbers.get(1-choice));
			choices.add(start);
		} else {
			if(numbers.get(0) == start) {
				choices.add(numbers.get(1));
				choices.add(start);
			} else {
				choices.add(numbers.get(0));
				choices.add(start);
			}
		}

		pickStart = true;
		start = choices.get(0);
		for(int b = 0; b < choices.size(); b++) {
			if(!pickStart) {
				fromToMap.put(choices.get(b-1), choices.get(b));
			//	log.info("EmpId {}.  is gifting to EmpId  {}.", choices.get(b-1) , choices.get(b));
				if(choices.get(b).equals(start)) {
					pickStart = true;
				}
			} else {
				start = choices.get(b);
				pickStart = false;
			}
		}
		return fromToMap;
	}

	private List<Integer> getAllEmpId(){
		List<Employee> emps = employeeRepository.findAll();
		List<Integer> empIdList = new ArrayList<Integer>();
		for (int i = 0; i <emps.size(); i++) {
			empIdList.add(emps.get(i).getId());
		}
		return empIdList;
	}

}
