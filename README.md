hw6: Concept Visualizer
===
For this homework assignment you will study cross-lingual relationships in Wikipedia.

Wikipedia offers encyclopedias in 287 different languages, from English to Arabic to Inuktitut.
You will identify the most popular articles in each language, and uncover the articles in other
  languages that reference the same concept.

Your program will rely on the [wikAPIdia software library](https://github.com/shilad/wikAPIdia)
developed by Shilad, Rebecca, and [Brent Hecht](http://www.brenthecht.com/).
wikAPIdia is a large, complex project, but we've created a helper class called WikapidiaWrapper
to make your life easier.

As you work on this assignment, you'll practice interacting with java Lists.
You'll create them, loop over them, and search them.

## Getting started
Fork and clone this repository.

If you work on lab computers:

1. Create a directory called /Users/<your name>/wikAPIdia
2. Copy the /db directory on the lab macs into the directory you created.

If you work on your laptop:

1. Download the compressed 1.5 GB [WikAPIdia database](http://macademia.macalester.edu/shilad/wikAPIdiaDB.tar.bz2).
2. Uncompress the file, you'll find a directory called "db"
3. Move the directory under your hw6 directory

Make sure that things are installed correctly:

1. Open PopularArticleAnalyzer.java in IntelliJ
2. Take a look at the main() method. It constructs a WikAPIdiaWrapper object. 
3. The WikAPIdia wrapper object takes a String representing the parent directory of the "db" folder. 
Change this to make it correct.
This will be "/Users/<yourname>/wikAPIdia" on a lab computer, or your hw6 directory on your laptop.
4. Run the class as an application (not Applet): PopularArticleAnalyzer.main(). 
You should see information about the [Apple article](http://simple.wikipedia.org/wiki/Apple) in simple English Wikipedia.

## Task 1:
Tasks;
1. Popular article analyzer
2. Construct label
3. Highlight

Create small sample db with ~10K articles per languagex.
