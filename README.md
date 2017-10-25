# WebIndexer
WebIndexer is a web that crawl different web pages and index its contents for searching words.
This process is repeated for each outlinks of the web page.

### Installing

A step by step series of examples that tell you have to get a development env running

1- Install Maven dependencies

2- Install Bower dependencies

```
bower install
```

### Configuration

Configure variable of application in file:

```
resources/application.properties
```
These variables are:
```
1- depth: Maxiumum depth for crawling process
2- directoryPath: Base direcotry path where are stored all indexes
1- indexesDirectoryPath: Directory path where are stored doduments with information of stored indexes
```