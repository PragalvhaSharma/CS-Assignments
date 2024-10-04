def read_keywords(keyword_file_name):
    """
    Reads a file containing tab-separated keyword-value pairs and
    returns a dictionary with keywords as keys and their corresponding
    values

    Parameters:
    - keyword_file_name: The name or path of the file to be read.

    Returns:
    - keyword_dic (dict): A dictionary with keywords as keys and their
      corresponding values as integers.
    - empty string (IO ERROR): If the file cannot be opened
    """

    try:
        # Attempt to open the specified keyword file in read mode
        keyword_file = open(keyword_file_name, 'r')

        # Initialize an empty dictionary to store keyword-value pairs
        keyword_dic = {}

        # Read the first line of the file
        file_line = keyword_file.readline()

        # Continue reading lines until the end of the file is reached
        while file_line != "":
            # Split the line into key and value using tab as a delimiter
            key, value = file_line.strip().split('\t')

            # Convert the value to an integer and add the key-value pair to the dictionary
            keyword_dic[key] = int(value)

            # Read the next line
            file_line = keyword_file.readline()

        # Close the file once all lines have been processed
        keyword_file.close()

        # Return the populated keyword dictionary
        return keyword_dic

    except IOError:
        # Handle the case where the file cannot be opened,
        # print an error message, and return an empty dictionary
        print(f"Could not open file {keyword_file_name}")
        return {}


def clean_tweet_text(tweet_text):
    """
    Cleans the input tweet text by removing non-alphabetic characters
    and converting all characters to lowercase.

    Parameters:
    - tweet_text (str): Tweet/Sentence

    Returns:
    - clean_string (str): Only alphabetic characters from the text in lowercase.
    """

    # Initialize an empty string to store the cleaned text
    clean_string = ""

    # Iterate through each character in the input tweet text
    for char in tweet_text:
        # Check if the character is alphabetic or a whitespace
        if char.isalpha() or char.isspace():
            # Convert the character to lowercase
            lower_text = char.lower()

            # Add the lowercase character to the cleaned string
            clean_string += lower_text

    # Return the final cleaned tweet text
    return clean_string


def calc_sentiment(tweet_text, keyword_dict):
    """
    Calculates the sentiment score of a tweet

    Parameters:
    - tweet_text (str): The text of the tweet which we want to calculate
    - keyword_dict (dict): A dictionary containing keywords and their associated sentiment scores.

    Returns:
    - sentiment_score (int): The calculated sentiment score for the tweet based on the dictionary.
    """

    # Initialize the sentiment score to zero
    sentiment_score = 0

    # Split the tweet_text into words or tokens
    tweet_words = tweet_text.split()

    # Iterate through each word in the tweet
    for word in tweet_words:
        if word in keyword_dict:
            # Add the sentiment score of the word to the overall sentiment score
            sentiment_score += keyword_dict[word]

    # Return the calculated sentiment score for the tweet
    return sentiment_score


def classify(score):
    """
    Classifies a sentiment score into categories: "positive," "neutral," or "negative."

    Parameters:
    - score (int): The sentiment score to be classified.

    Returns:
    - classification (str): The sentiment classification with as "positive," "neutral," or "negative."
    """
    # Check if the score is greater than 0
    if score > 0:
        return "positive"

    # Check if the score is equal to 0
    elif score == 0:
        return "neutral"

    # If the score is less than 0, classify as "negative"
    else:
        return "negative"


def read_tweets(tweet_file_name):
    """
    Reads a file containing tweets' information, extracts relevant details, and returns a list of dictionaries
    for every tweet with information

    Parameters:
    - tweet_file_name (str): The name or path of the file containing tweet information.

    Returns:
    - output_list (list): A list of dictionaries, each representing a tweet's information.
    - empty List (IO ERROR): If the file cannot be opened
    """

    try:
        # Initialize an empty list to store dictionaries representing tweet information
        output_list = []

        # Attempt to open the specified tweet file in read mode
        tweet_file = open(tweet_file_name, "r")
        tweet_line = tweet_file.readline()

        # Continue reading lines until the end of the file is reached
        while tweet_line != "":
            # Initialize a dictionary to store information about the current tweet
            info_dict = {'date': None, 'text': None, 'user': None, 'retweet': None, 'favorite': None,
                         'lang': None, 'country': None, 'state': None, 'city': None, 'lat': None, 'lon': None}

            # Split the line into components using comma as a delimiter
            separated_tweet = tweet_line.split(",")

            # Populate the dictionary with relevant information from the separated components
            info_dict['date'] = separated_tweet[0]
            info_dict['text'] = clean_tweet_text(separated_tweet[1])
            info_dict['user'] = separated_tweet[2]
            info_dict['retweet'] = int(separated_tweet[3])
            info_dict['favorite'] = int(separated_tweet[4])
            info_dict['lang'] = separated_tweet[5]
            info_dict['country'] = separated_tweet[6]
            info_dict['state'] = separated_tweet[7]
            info_dict['city'] = separated_tweet[8]
            info_dict['lat'] = separated_tweet[9]
            info_dict['lon'] = separated_tweet[10].strip("\n")

            # Convert latitude and longitude to float if they are not "NULL"
            if info_dict['lat'] != "NULL":
                info_dict['lat'] = float(separated_tweet[9])
            if info_dict['lon'] != "NULL":
                info_dict['lon'] = float(separated_tweet[10])

            # Append the tweet's information dictionary to the output list
            output_list.append(info_dict)

            # Read the next line
            tweet_line = tweet_file.readline()

        # Close the file once all lines have been processed
        tweet_file.close()

        # Return the list of tweet information dictionaries
        return output_list

    except IOError:
        # Handle the case where the file cannot be opened,
        # Print an error message, and return an empty list
        print(f"Could not open file {tweet_file_name}")
        return []


def calculate_average(number_list):
    """
    Calculates the average of a list of numbers for avg_favorite, avg_retweet and avg_sentiment

    Parameters:
    - number_list (list): The list of numbers for which the average is to be calculated.

    Returns:
    - average (float): The calculated average of the numbers. If the average is 0, returns 'NAN'.
    """

    list_total = sum(number_list)
    list_average = list_total / len(number_list)

    # Return the calculated average
    return list_average


def get_value_key(item):
    """
    Key function for sorting a list of tuples based on the second element.

    Parameters:
    - item: A tuple where the key is the second element.

    Returns:
    - item[1]: The second element of the tuple, used as the key for sorting.
    """

    # Return the second element of the tuple (item[1]) as the sorting key
    return item[1]


def make_report(tweet_list, keyword_dict):
    """
    Generates a report based on the sentiment analysis of a list of tweets.

    Parameters:
    - tweet_list (list): A list of dictionaries, each representing information about a tweet.
    - keyword_dict (dict): A dictionary containing keywords and their associated sentiment scores.

    Returns:
    - info_dict (dict): A dictionary containing various statistics and metrics derived from the provided tweet list
    including avg_favorite, avg_retweet, avg_sentiment, num_favorite e.t.c
    """

    # Initialize variables to track different metrics
    total_tweets = 0
    positive_tweets = 0
    negative_tweets = 0
    neutral_tweets = 0
    favourite_scores = []
    retweet_scores = []
    all_scores = []
    countries_dic = {}
    top_countries = ''

    # Initialize the output dictionary with placeholders for different statistics
    info_dict = {'avg_favorite': None, 'avg_retweet': None, 'avg_sentiment': None, 'num_favorite': None,
                 'num_negative': None, 'num_neutral': None, 'num_positive': None, 'num_retweet': None,
                 'num_tweets': None, 'top_five': None}

    # Iterate through each tweet dictionary in the tweet list
    for dictionary in tweet_list:
        # Calculate the sentiment score for the tweet
        sentiment_score = calc_sentiment(dictionary['text'], keyword_dict)

        # Classify the sentiment score
        classified_score = classify(sentiment_score)

        # Update metrics based on sentiment classification
        if classified_score == 'positive':
            positive_tweets += 1
        elif classified_score == 'negative':
            negative_tweets += 1
        else:
            neutral_tweets += 1

        # Add the sentiment score to the list of all scores
        all_scores.append(sentiment_score)

        # Increment the total number of tweets
        total_tweets += 1

        # Track sentiment scores for tweets with favorites and retweets
        if dictionary['favorite'] > 0:
            favourite_scores.append(calc_sentiment(dictionary['text'], keyword_dict))
        if dictionary['retweet'] > 0:
            retweet_scores.append(calc_sentiment(dictionary['text'], keyword_dict))

        # Track the country of the tweet for country-based analysis
        current_country = dictionary['country']

        # Track the country and associated sentiment scores
        if current_country not in countries_dic:
            countries_dic[current_country] = {"total_score": sentiment_score, "count": 1}
        else:
            countries_dic[current_country]["total_score"] += sentiment_score
            countries_dic[current_country]["count"] += 1

    # Calculate and round average sentiment, retweet, and favorite scores
    # Update info_dict with various metrics

    # Calculate the number of tweets with favorites and update the average favorite score
    info_dict['num_favorite'] = len(favourite_scores)
    if info_dict['num_favorite'] == 0:
        info_dict['avg_favorite'] = "NAN"
    else:
        info_dict['avg_favorite'] = round(calculate_average(favourite_scores), 2)

    # Calculate the number of tweets with retweets and update the average retweet score
    info_dict['num_retweet'] = len(retweet_scores)
    if info_dict['num_retweet'] == 0:
        info_dict['avg_retweet'] = "NAN"
    else:
        info_dict['avg_retweet'] = round(calculate_average(retweet_scores), 2)

    # Update the total number of tweets and calculate the average sentiment score
    info_dict['num_tweets'] = total_tweets
    if info_dict['num_tweets'] == 0:
        info_dict['avg_sentiment'] = "NAN"
    else:
        info_dict['avg_sentiment'] = round(calculate_average(all_scores), 2)

    # Update the number of tweets with negative, neutral, and positive sentiment
    info_dict['num_negative'] = negative_tweets
    info_dict['num_neutral'] = neutral_tweets
    info_dict['num_positive'] = positive_tweets

    # Remove the placeholder entry for "NULL" if present in the countries dictionary
    if "NULL" in countries_dic:
        countries_dic.pop("NULL")

    # Calculate and store average sentiment scores for each country
    average_scores_countries = {}
    for country, data in countries_dic.items():
        total_score = data["total_score"]
        count = data["count"]
        average_score = total_score / count
        average_scores_countries[country] = average_score

    # Sort the countries based on average sentiment scores in descending order
    average_scores_countries = dict(sorted(average_scores_countries.items(), key=get_value_key, reverse=True))

    # Prepare the top five countries string
    if len(average_scores_countries) >= 5:
        for key in list(average_scores_countries.keys())[:5]:
            top_countries += key + ', '
        if top_countries:
            top_countries = top_countries[:-2]
        info_dict['top_five'] = top_countries
    else:
        for key in average_scores_countries:
            top_countries += key + ', '
        if top_countries:
            top_countries = top_countries[:-2]
        info_dict['top_five'] = top_countries

    # Return the generated report
    return info_dict


def write_report(report, output_file):
    """
    Writes a sentiment analysis report to a specified output file.

    Parameters:
    - report (dict): A dictionary containing various statistics and metrics from the make report function.
    - output_file (str): The name or path of the file where the report will be written.

    Returns:
    - None

    Prints a success message if the report is successfully written; otherwise, prints an error message.
    """
    try:
        # Open the specified output file in write mode
        final_file = open(output_file, "w")

        # Write various statistics and metrics to the file
        final_file.write(f"Average sentiment of all tweets: {report['avg_sentiment']}\n")
        final_file.write(f"Total number of tweets: {report['num_tweets']}\n")
        final_file.write(f"Number of positive tweets: {report['num_positive']}\n")
        final_file.write(f"Number of negative tweets: {report['num_negative']}\n")
        final_file.write(f"Number of neutral tweets: {report['num_neutral']}\n")
        final_file.write(f"Number of favorited tweets: {report['num_favorite']}\n")
        final_file.write(f"Average sentiment of favorited tweets: {report['avg_favorite']}\n")
        final_file.write(f"Number of retweeted tweets: {report['num_retweet']}\n")
        final_file.write(f"Average sentiment of retweeted tweets: {report['avg_retweet']}\n")
        final_file.write(f"Top five countries by average sentiment: {report['top_five']}\n")

        # Print a success message indicating the successful report writing
        print(f"Wrote report to {output_file}")

        # Close the file
        final_file.close()

    except IOError:
        # Handle the case where the file cannot be opened,
        # print an error message, and continue with program execution
        print(f"Could not open file {output_file}")