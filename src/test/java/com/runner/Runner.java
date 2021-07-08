package com.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="addFeature",glue="com.defination",monochrome = true,dryRun=false,plugin= {"pretty"},tags = {"@recieveStation"})
public class Runner {
	
	


}
