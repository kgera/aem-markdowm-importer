/**
 * Copyright 2017 Adobe Systems Incorporated. All rights reserved.
 * This file is licensed to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.adobe.udp.markdownimporter.services;

import io.adobe.udp.markdownimporter.MarkdownPageData;
import io.adobe.udp.markdownimporter.flexmarkExtensions.GithubHostedImagePrefixer;

import java.io.Reader;
import java.util.List;

public interface MarkdownParserService {
	
	public MarkdownPageData parseMarkdownFile(Reader file, MarkdownPageData pageData, List<String> urls,
			GithubHostedImagePrefixer urlPrefixer);

}
