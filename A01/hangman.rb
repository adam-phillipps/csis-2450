require 'random_word_generator'
require 'asciiart'

class Hangman
  def initialize
    input = ''
    until input.eql? '3'
      puts 'Start (has defaults) (1) or pick a word length and number of guesses (2) or exit (3)?'
      input = gets.chomp
      case input
      when '1'
        unless @challenge_word
          @challenge_word = ch_word((rand(1..4) * 3))
        end
        play_game
      when '2'
        puts "1. word length (1-12)\n2. guesses"
        case gets.chomp
        when '1'
          puts 'chose the word length, 1 to 12'
          input = gets.chomp.to_i
          @challenge_word = ch_word(input)
        when '2'
          puts 'how many guesses do you want?'
          @guesses = gets.chomp.to_i
        end
      end
    end
  end

  def ch_word(word_size)
    RandomWordGenerator.of_length(word_size).split(//)
  end

  def play_game
    @guesses ||= 6
    @user_guess = Array.new(@challenge_word.count, '_')
    until (@guesses == 0 || !(@user_guess.any? { |l| l.eql? '_' }))
      puts "\nYou have #{@guesses} guesses...Go!"
      puts @user_guess.join(' ')
      input = gets.chomp
      find_and_replace(input)
      puts @user_guess.join(' ')
    end
    puts "\n"
    puts @challenge_word.join(' ')
    puts "\n"
    unless @user_guess.any? { |l| l.eql? '_' }
      a=AsciiArt.new('./winner.jpg')
      puts a.to_ascii_art
    end
    @challenge_word = nil
    @guesses = nil 
  end

  def find_and_replace(input)
    if @challenge_word.any? { |l| l.eql? input }
      corrects = @challenge_word.each_index.select{ |i| @challenge_word[i] == input }
      corrects.each { |i| @user_guess[i] = input }
    else
      if (@guesses -= 1) > 0
        puts "nope...you have #{@guesses} guesses left"
      else
        a=AsciiArt.new('./hangman.jpg')
        puts a.to_ascii_art
      end
    end
  end
end

Hangman.new