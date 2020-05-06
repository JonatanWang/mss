# Practices: Movie search service (mss) 
![large](https://img.shields.io/badge/size-large-red.svg?longCache=true&style=for-the-badge)

![backend](https://img.shields.io/badge/backend-blue.svg?longCache=true&style=for-the-badge)
![fullstack](https://img.shields.io/badge/fullstack-red.svg?longCache=true&style=for-the-badge)
![Database Design](https://img.shields.io/badge/database_design-darkred.svg?longCache=true&style=for-the-badge)
![Data indexing](https://img.shields.io/badge/data_indexing-darkgreen.svg?longCache=true&style=for-the-badge)
![API Design](https://img.shields.io/badge/api_design-purple.svg?longCache=true&style=for-the-badge)
![REST API](https://img.shields.io/badge/rest_api-darkblue.svg?longCache=true&style=for-the-badge)

You are going to develop a search service for movies based on a [subset of IMDb data](https://www.imdb.com/interfaces/) available to download. 

## Step 1 - Import data to database
In short this includes:
 * Develop import functionality
 * Design database

Select data set(s) of your choice from [IMDb](https://www.imdb.com/interfaces/) to support searching for movies. Then decide on what database you want to import this data and also design the database model. This will finally require you to develop import functionality.
 
 __HINT!__ _Did you know there are plenty of databases available as docker images at [Docker Hub](https://hub.docker.com/)_

## Step 2 - Develop search API
First of all, develop the domain model representation in your application. This could potentionally differ from the model being used in the database. Then expose the data making it searchable via an API.

## Step 3 - Index your data
Do you think we can perform any better when it comes to search? Well, now we will at least try. 

Index your data to support for example _full text search_.

__HINT!__ _Elasticsearch is good at these type of problem domains._

## Step 4 - Next attempt creating search API
Refactor your search API to use the indexed data instead.

## Extra materials
You still have time an energy to take your solution ever further?! ;) Well, here you go!

### Step 5 - Support recommendations
Give movie recommendations to users based on what they are interested in (based on their previous search history).

### Step 6 - Web based UI
Develop a web based GUI for your service.

__HINT!__ _Single Page Application (SPA) is a good choice._

