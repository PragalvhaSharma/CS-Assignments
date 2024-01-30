# Import the sentiment_analysis module
from sentiment_analysis import *


# Main function to execute the sentiment analysis
def main():
    # Input filenames for keyword, tweet, and output report
    keyword_file = input("Input keyword filename (.tsv file): ")
    tweet_file = input("Input tweet filename (.csv file): ")
    output_file = input("Input filename to output report in (.txt file): ")

    # Validate file extensions
    if not keyword_file.endswith('.tsv'):
        raise Exception("Must have tsv file extension!")

    if not tweet_file.endswith('.csv'):
        raise Exception("Must have csv file extension!")

    if not output_file.endswith('.txt'):
        raise Exception("Must have txt file extension!")

    # Read keywords from the provided file
    keyword_dict = read_keywords(keyword_file)
    # Raise an exception if empty
    if not keyword_dict:
        raise Exception("Keyword dictionary is empty!")

    # Read tweets from the provided file
    tweet_data = read_tweets(tweet_file)
    # Raise an exception if empty
    if not tweet_data:
        raise Exception("Tweet list is empty!")

    # Generate a sentiment analysis report based on keywords and tweets
    final_report = make_report(tweet_data, keyword_dict)

    # Write the final sentiment analysis report to the specified output file
    write_report(final_report, output_file)


# Call the main function to execute the sentiment analysis
main()
